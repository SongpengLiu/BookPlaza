package com.example.bookplaza.searchResults;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.bookplaza.MainActivity;
import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.io.Reader;
import com.example.bookplaza.search.Exp;
import com.example.bookplaza.search.Parser;
import com.example.bookplaza.search.Tokenizer;
import com.example.bookplaza.trees.AttributesLoader;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Handle search results
 *
 * @author Yucheng Zhu
 */
public class SearchResultsHandler {
    /**
     * Given book indices in the current group string, read all the books' attributes
     *
     * @author Yucheng Zhu
     * @param context An Android object
     * @param selectedBookGroup All the book's ids in the current group as a string
     * @return Books' attributes
     */
    public BooksAttributes getBooksAttributes(Context context, String selectedBookGroup) {
        // Get book indices in the current group
        HashSet<Integer> booksIndices;
        if (selectedBookGroup == null) {
            booksIndices = AttributesLoader.ALL_INDICES;
        } else {
            String bookIndicesInTheCurrentGroup;
            try {
                bookIndicesInTheCurrentGroup = Reader.read(Reader.assetsFolder(context, selectedBookGroup));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            booksIndices = IndicesReader.toIndices(bookIndicesInTheCurrentGroup);
        }

        // load data into trees and HashMaps
        AttributesLoader attributesLoader = new AttributesLoader();
        BooksAttributes attributes;
        try {
            attributes = attributesLoader.readBooksToAttributes(
                    context,
                    Data.BOOK_CSV_FILE,
                    booksIndices
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return attributes;
    }

    /**
     * Given input and book indices in the currrent group string, find all the books' ids
     *
     * @author Yucheng Zhu
     * @param context An Android object
     * @param input User's search string
     * @param attributes All the book's attributes
     * @return All the books' ids
     */
    public ArrayList<Integer> getResultsIndices(Context context, String input, BooksAttributes attributes) {

        // Parse input and attributes using grammar
        ArrayList<TreeData<Integer>> treeDataList = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp(attributes);
            // Parse grammar
            treeDataList = expression.evaluateBooks(attributes);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            // go back to search page
            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
            context.startActivity(intent);
        }

        // Turn a list of tree data into a list of integers
        // FIXME: Get books from ids in the tree directly
        ArrayList<Integer> resultsIndexList = new ArrayList<>();
        for (TreeData<Integer> treeData: treeDataList) {
            resultsIndexList.add(treeData.getId());
        }

        // book indices
        return resultsIndexList;
    }

}
