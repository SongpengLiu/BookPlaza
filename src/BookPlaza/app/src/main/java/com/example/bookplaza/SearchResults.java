package com.example.bookplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.bookplaza.data.Book;
import com.example.bookplaza.data.Books;
import com.example.bookplaza.io.HashMapReader;
import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.io.Reader;
import com.example.bookplaza.io.Data;
import com.example.bookplaza.searchResults.SearchResultsHandler;
import com.example.bookplaza.surprise.BooksGroupsType;
import com.example.bookplaza.surprise.GroupChanger;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.Library;
import com.example.bookplaza.user.UserOperation;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Activity for displaying search results
 *
 * Users can sort items by price or book name
 * This page will recommend top 2 rating books and advertise 2 books
 *
 * @author Xiangji Kong
 */
public class SearchResults extends AppCompatActivity {

    private List<Book> bookResultList; // search result
    private List<String> resultList;
    private ArrayList<String> sortConditionList;
    private ArrayAdapter resultListAdapter;
    private ArrayAdapter sortConditionListAdapter;
    private Books books;
    private Books advertisedBooks;
    private DecimalFormat priceFormat = new DecimalFormat("00.00");
    private BooksAttributes attributes;// books' attributes in trees and HashMaps

    /**
     * Create the search results page
     * @author Xiangji Kong
     * @param savedInstanceState Default Android parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get intent (the input words) sent from MainActivity
        String input = getIntent().getStringExtra("input");
        String selectedBookGroup = getIntent().getStringExtra("selectedBookGroup"); // bookGroup has value = null / DailyReading / HotFeed / Explore
        System.out.println("SearchResults.onCreate(): selectedBookGroup="+selectedBookGroup);

        // Get book indices in the current group
        SearchResultsHandler searchResultsHandler = new SearchResultsHandler();

        // Read books' attributes
        BooksAttributes attributes = searchResultsHandler.getBooksAttributes(this, selectedBookGroup);

        // Read book group changes
        HashSet<ArrayList<Integer>> booksGroupsChangesMap;
        try {
            HashMapReader<HashSet<ArrayList<Integer>>> hashMapReader = new HashMapReader<>();
            booksGroupsChangesMap = hashMapReader.readInfoForBooksChangedGroups(
                    Reader.dataFolder(this, "groupChanges.json")
            );
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("First time run this app? Welcome!");
            booksGroupsChangesMap = new HashSet<>();
        }
//        System.out.println("SearchResults.onCreate(): booksGroupsChangesMap="+booksGroupsChangesMap);

        // add or remove attributes according to the changed books groups
        Library library = null;
        try {
            library = new Library(Reader.assetsFolder(this, Data.BOOK_CSV_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GroupChanger groupChanger = new GroupChanger(library);
        // load all books' groups' indices
        HashSet<Integer>[] allGroupsIndices = groupChanger.loadAllBooksGroupsIndices(this);

        if (selectedBookGroup != null) {
            int selectedBookGroupIndex = BooksGroupsType.fromValue(selectedBookGroup).getGroupCount();
            System.out.println("SearchResults.onCreate(): selectedBookGroupIndex=" + selectedBookGroupIndex);
            HashSet<Integer> currentGroupsIndices = allGroupsIndices[selectedBookGroupIndex];

            // add/remove books changed group to the current books' attributes
            for (ArrayList<Integer> move : booksGroupsChangesMap) {
                if (!currentGroupsIndices.contains(move.get(2))) {
                    groupChanger.add(
                            move.get(0),
                            attributes
                    );
                    System.out.println("SearchResults.onCreate(): adding " + move.get(0) + " to attributes");
                } else if (currentGroupsIndices.contains(move.get(1))) {
                    groupChanger.delete(
                            move.get(0),
                            attributes
                    );
                    System.out.println("SearchResults.onCreate(): removing " + move.get(0) + " from attributes");
                }
            }
        }


        // Get the book indices in the search result
        ArrayList<Integer> resultsIndexList = searchResultsHandler.getResultsIndices(
                this, input, attributes);

        // Get all books' attributes given their indices
        books = UserOperation.getInstance().searchBooks(resultsIndexList);

        // gat all advertised books
        HashSet<Integer> booksIndices;
        String bookIndicesInTheCurrentGroup;
        try {
            bookIndicesInTheCurrentGroup = Reader.read(Reader.assetsFolder(this, "Advertised"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        booksIndices = IndicesReader.toIndices(bookIndicesInTheCurrentGroup);
        ArrayList<Integer> advertisedBooksIndicesList = new ArrayList<>(booksIndices);

        // find intersection of advertisedBooksIndicesList and resultsIndexList
        advertisedBooksIndicesList.retainAll(resultsIndexList);
        advertisedBooks = UserOperation.getInstance().searchBooks(advertisedBooksIndicesList);

        // save search history
        UserOperation.getInstance().saveSearchHistory(input);

        // sample result
        resultList = new ArrayList<>();
        bookResultList = new ArrayList<>();
        resultList.add("sample");

        // sorting condition
        sortConditionList = new ArrayList<>();
        sortConditionList.add("Title");
        sortConditionList.add("Price Low to High");
        sortConditionList.add("Price High to Low");

        // add ListView to result list
        ListView urlListView = (ListView) findViewById(R.id.resultList);
        resultListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList);
        urlListView.setAdapter(resultListAdapter);

        // Set an OnItemClickListener for the result list
        urlListView.setOnItemClickListener(listListener);

        // add ListView to sort condition list
        Spinner sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        sortConditionListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sortConditionList);
        sortSpinner.setAdapter(sortConditionListAdapter);

        // Set an OnItemClickListener for the sort condition list
        sortSpinner.setOnItemSelectedListener(spinnerListener);
    }

    // OnItemClickListener for the result list
    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Handle item clicked
            String selectedItem = bookResultList.get(i).toString();
            int index = bookResultList.get(i).getIndex();
            System.out.println("SearchResults.onItemClick(): index="+index);
            System.out.println("SearchResults.onItemClick(): selectedItem="+selectedItem);

            // open ItemDetail page using an Intent
            Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
            intent.putExtra("resultItem", selectedItem);
            intent.putExtra("resultIndex", index);
            startActivity(intent);
        }
    };

    // OnItemClickListener for the sort condition list
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        // Different options selected
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:{
                    books.sortByName();
                    refreshResult();
                    break;
                }
                case 1:{
                    books.sortByPriceLowToHigh();
                    refreshResult();
                    break;
                }
                case 2:{
                    books.sortByPriceHighToLow();
                    refreshResult();
                    break;
                }
            }
        }

        // The original selection was selected in the drop-down option, which means that the option has not been changed.
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    public void refreshResult(){
        resultList.clear();
        List<Book> bookList = books.getBooksList();
        List<Book> advertisedBookList = advertisedBooks.getBooksList();

        List<String> elseResultList = new ArrayList<>();
        List<Book> elseBookResultList = new ArrayList<>();

        // get top 2 rating
        books.sortByRating();
        List<Book> recommendList = books.getBooksList();
        if (recommendList.size() >= 2) {
            recommendList = recommendList.subList(0,2);
        }
        else {
            recommendList = recommendList.subList(0,recommendList.size());;
        }

        // advertise 2 books
        if (advertisedBookList.size() >= 2) {
            advertisedBookList = advertisedBookList.subList(0,2);
        }
        else {
            advertisedBookList = advertisedBookList.subList(0,advertisedBookList.size());;
        }

        // load results into resultList
        for (Book b: advertisedBookList) {
            resultList.add("\nAdvertised!\nPrice: $"+ priceFormat.format(b.getPrice())+ " | Title: "+ b.getName() + "\n");
            bookResultList.add(b);
        }
        for (Book b: bookList) {
            if (recommendList.contains(b)) {
                resultList.add("\nRecommend to you!\nPrice: $"+ priceFormat.format(b.getPrice())+ " | Title: "+ b.getName() + "\n");
                bookResultList.add(b);
            }
            else {
                elseResultList.add("Price: $"+ priceFormat.format(b.getPrice())+ " | Title: "+ b.getName());
                elseBookResultList.add(b);
            }
        }
        resultList.addAll(elseResultList);
        bookResultList.addAll(elseBookResultList);


        // Display search quantity
        int searchNum = resultList.size();
        TextView userWelcome = (TextView) findViewById(R.id.searchNumText);
        userWelcome.setText("We found " + searchNum + " results");

        resultListAdapter.notifyDataSetChanged();
    }
}