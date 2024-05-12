package com.example.bookplaza.trees;

import android.content.Context;
import com.example.bookplaza.data.Book;
import com.example.bookplaza.io.Reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringJoiner;

import static com.example.bookplaza.trees.BooksAttributes.addBookToDict;
import static com.example.bookplaza.trees.BooksAttributes.removeBookFromDict;


/**
 * Load book data into trees and hashmaps so that they can be sorted and found in O(log n) or O(1).
 * All string attributes are in HashMaps to be found in O(1)
 * All number attributes are in trees to be found in O(log n)
 * @author Yucheng Zhu
 */
public class AttributesLoader {
    public static final HashSet<Integer> ALL_INDICES = null;
    private final static int FIELDS = 8;

    /**
     * Read books from the storage and put them into the appropriate container.
     * String values are in HashMaps and number values are in trees
     * @author Yucheng Zhu
     * @param inputStream Input stream to book data
     * @return The books' attributes as trees and sets
     * @throws IOException When file not found
     */
    public BooksAttributes readBooksToAttributes(
            InputStream inputStream,
            HashSet<Integer> booksIndices
    ) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        BooksAttributes attributes = new BooksAttributes();
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


            // -- Check that the book should be read
            if (booksIndices != ALL_INDICES && !booksIndices.contains(id)) continue;

            Book book = toBook(splitBookFields, id);

            if (book != null) {
                // add book's attributes to trees (numerical) and sets (strings)
                addToAttributes(attributes, book);
            } else {
                System.out.println("AttributesLoader.readBooksToAttributes(): splitBookFields"+ Arrays.toString(splitBookFields));
            }

        }

        return attributes;
    }

    private static String joinBookFields(String[] splitBookFields) {
            StringJoiner joinedFields = new StringJoiner(",");
            for (int i = FIELDS-1; i < splitBookFields.length; i++) {
                joinedFields.add(splitBookFields[i]);
            }
            return joinedFields.toString();
    }

    public static Book toBook(String[] splitBookFields, int id) {
        // -- Check that values are legal

        // Wrong fields' count
        if (splitBookFields.length != FIELDS) {
            if (splitBookFields.length > FIELDS) {
                // join URL string
                splitBookFields[7] = joinBookFields(splitBookFields);
            } else {
                return null;
            }
        }

        if (!isString(splitBookFields[0])) return null; // author
//        if (!isString(splitBookFields[1])) return null; // title
        if (!isFloat(splitBookFields[2])) return null; // rating
        if (!isInteger(splitBookFields[3])) return null; // rater count
        if (!isString(splitBookFields[4])) return null; // genre
        if (!isFloat(splitBookFields[5])) return null; // best price
        if (!isString(splitBookFields[6])) return null; // seller
        if (!isString(splitBookFields[7])) return null; // url




        // create a new book object
        return new Book(
                id, splitBookFields[1], splitBookFields[0],
                Float.parseFloat(splitBookFields[2]), Integer.parseInt(splitBookFields[3]), Float.parseFloat(splitBookFields[5]),
                splitBookFields[4], splitBookFields[6], splitBookFields[7]
        );
    }

    /**
     * Read books from the storage and put them into the appropriate container.
     * String values are in HashMaps and number values are in trees
     * This method reads books from an Android class
     * @author Yucheng Zhu
     * @param context An Android object from which the book data file is read
     * @param bookCsvPath CSV file where books' data are contained
     * @return The books' attributes as trees and sets
     * @throws IOException When file not found
     */
    public BooksAttributes readBooksToAttributes(Context context, String bookCsvPath, HashSet<Integer> booksIndices) throws IOException {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            context.getAssets().open(bookCsvPath),
                            StandardCharsets.UTF_8
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return readBooksToAttributes(Reader.assetsFolder(context, bookCsvPath), booksIndices);
    }

    public static void addToAttributes(BooksAttributes attributes, Book book) {
        int bookId = book.getIndex();

        // -- Add current book's attributes to each tree/hash map
        addBookToDict(attributes.getAuthors(), book.getAuthor(), bookId); // author
        addBookToDict(attributes.getTitles(), book.getName(), bookId); // title
        attributes.getRatings().insert(new TreeData<>(book.getReviewScore(), bookId)); // rating
        attributes.getRatersCounts().insert(new TreeData<>(book.getReviewPeopleNumber(), bookId)); // rater count
        addBookToDict(attributes.getGenres(), book.getGenre(), bookId); // genre
        attributes.getBestPrices().insert(new TreeData<>(book.getPrice(), bookId)); // best price
        addBookToDict(attributes.getSellers(), book.getSeller(), bookId); // seller
        addBookToDict(attributes.getUrls(), book.getUrl(), bookId); // url
    }

    public static void removeFromAttributes(BooksAttributes attributes, Book book) {
        int bookId = book.getIndex();

        // -- Add current book's attributes to each tree/hash map
        removeBookFromDict(attributes.getAuthors(), book.getAuthor(), bookId); // author
        removeBookFromDict(attributes.getTitles(), book.getName(), bookId); // title
        attributes.getRatings().delete(new TreeData<>(book.getReviewScore(), bookId)); // rating
        attributes.getRatersCounts().delete((new TreeData<>(book.getReviewPeopleNumber(), bookId))); // rater count
        removeBookFromDict(attributes.getGenres(), book.getGenre(), bookId); // genre
        attributes.getBestPrices().delete((new TreeData<>(book.getPrice(), bookId))); // best price
        removeBookFromDict(attributes.getSellers(), book.getSeller(), bookId); // seller
        removeBookFromDict(attributes.getUrls(), book.getUrl(), bookId); // url
    }

    /**
     * Check a string is an integer
     * @author Yucheng Zhu
     * @param str String to check
     * @return True if is integer, otherwise false
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check a string is a float number
     * @author Yucheng Zhu
     * @param str String to check
     * @return True if is float, otherwise false
     */
    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check a string is a string (i.e. neither an integer nor a float)
     * @author Yucheng Zhu
     * @param str String to check
     * @return True if is string, otherwise false
     */
    public static boolean isString(String str) {
        return (!isInteger(str) && !isFloat(str));
    }
}
