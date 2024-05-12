package com.example.bookplaza;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.bookplaza.dictTree.DictTree;
import com.example.bookplaza.dictTree.NumbersPair;
import com.example.bookplaza.io.*;
import com.example.bookplaza.surprise.BooksGroupsType;
import com.example.bookplaza.surprise.GroupChanger;
import com.example.bookplaza.surprise.MeanPreferencesValuesMapUpdater;
import com.example.bookplaza.trees.Library;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Activity for displaying detail of selected book
 *
 * Users can click "buy now" button to browse the item in a real-world website
 *
 * @author Xiangji Kong
 */
public class ItemDetail extends AppCompatActivity {

    public ArrayList<String> itemPropertyList;
    public ArrayAdapter itemPropertyAdapter;
    private String URL;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get intent (the item in result list) sent from SearchResults
        String resultItem = getIntent().getStringExtra("resultItem");
        int clickedBookIndex = getIntent().getIntExtra("clickedBookIndex", -1);

        // -- track book groups change
        // load books' constants
        Data data = Data.getInstance();

        // load library
        Library library = null;
        try {
            library = new Library(Reader.assetsFolder(this, Data.BOOK_CSV_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // construct group changer
        GroupChanger groupChanger = new GroupChanger(library);

        // load preference values
        MeanPreferencesValuesMapUpdater meanPreferencesValuesMapUpdater;
        try {
            meanPreferencesValuesMapUpdater = new MeanPreferencesValuesMapUpdater(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // update preference values after a book is clicked and return the old and new groups
        HashSet<ArrayList<Integer>> oldAndNewBooksGroup =
                groupChanger.recordBooksThatChangedGroups(
                        this,
                        data,
                        meanPreferencesValuesMapUpdater,
                        clickedBookIndex
                );

        // Write the change to file
//        HashSet<ArrayList<Integer>> test = new HashSet<>();
//        ArrayList<Integer> temp = new ArrayList<>();
//        temp.add(2230);
//        temp.add(0);
//        temp.add(2);
//        test.add(temp);
//
//        System.out.println("ItemDetail.oldAndNewBooksGroup="+oldAndNewBooksGroup);
        HashMapWriter hashMapWriter = new HashMapWriter();
        try {
            hashMapWriter.write(Writer.dataFolder(this, "groupChanges.json"), oldAndNewBooksGroup);
//            hashMapWriter.write(Writer.dataFolder(this, "groupChanges.json"), test);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // buy button
        Button loginButton = (Button) findViewById(R.id.buttonBuy);
        loginButton.setOnClickListener(buyButtonListener);

        // show item name
        TextView itemName = (TextView) findViewById(R.id.itemName);
        String bookName = resultItem.split(",")[1];
        itemName.setText(bookName);

        // Extract information from the passed-in String
        ArrayList<String> SinglePropertyList = new ArrayList<>();
        for (String property: resultItem.split(",")){
            SinglePropertyList.add(property);
        }


        // sample results
        itemPropertyList = new ArrayList<>();
        itemPropertyList.add("By " + SinglePropertyList.get(2) + ". Genre: " + SinglePropertyList.get(6));
        itemPropertyList.add(SinglePropertyList.get(3) + "/5.0 stars, reviewed by " + SinglePropertyList.get(4) + " readers");
        itemPropertyList.add("Best Price: $" + SinglePropertyList.get(5) + " at " + SinglePropertyList.get(7));
//        itemPropertyList.add("Buy Now: " + SinglePropertyList.get(8));
        URL = SinglePropertyList.get(8);

        // add ListView
        ListView urlListView = (ListView) findViewById(R.id.itemProperty);
        itemPropertyAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemPropertyList);
        urlListView.setAdapter(itemPropertyAdapter);

    }

    // buy button listener
    private View.OnClickListener buyButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // open a web page using an Intent
            Intent intent = new Intent(getApplicationContext(), ActivityWeb.class);
            intent.putExtra("URL", URL);
            startActivity(intent);
        }
    };
}