package com.example.bookplaza.trees;

import android.content.Context;
import com.example.bookplaza.data.Book;
import com.example.bookplaza.io.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Designed to find a book FAST
 * @author Yucheng Zhu
 */
public class Library {
    private Book[] shelf;
    private Data data;

    /**
     * Construct a library to find a book's basic info by its id FAST.
     * @author Yucheng Zhu
     * @param inputStream Book file
     * @throws IOException Cannot read the file
     */
    public Library(InputStream inputStream) throws IOException {
        this.shelf = update(inputStream);
        this.data = Data.getInstance();
    }

//    /**
//     * Construct a library to find a book's basic info by its id FAST.
//     * @author Yucheng Zhu
//     * @param inputStream Book file
//     * @throws IOException Cannot read the file
//     */
//    public Library(Context context, InputStream inputStream) throws IOException {
//        this.shelf = update(inputStream);
//        this.data = Data.getInstance(context);
//    }

    public Book get(int bookId) {
        return shelf[bookId];
    }

    public Book[] getShelf() {
        return shelf;
    }

    /**
     * Load all books from file.
     * Designed to find them by id FAST
     * @author Yucheng Zhu
     * @param inputStream Book file
     * @return Array of books
     * @throws IOException Cannot find the file
     */
    public Book[] update(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // construct an empty shelf
        shelf = new Book[Data.BOOKS_COUNT];

        //
        boolean firstLine = true; // skip field line
        int id = -2; // assign an id to each book

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            id++; // increment id in each line, starting from 0

            // skip field line
            if (firstLine) {
                firstLine = false;
                continue;
            }

            // split a book line into fields
            String[] splitBookFields = line.split(",");

            Book book = AttributesLoader.toBook(splitBookFields, id);

            // add to the library
            shelf[id] = book;
        }
        return shelf;
    }
}

