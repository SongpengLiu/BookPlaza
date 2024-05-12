package com.example.bookplaza.surprise;

import android.content.Context;
import com.example.bookplaza.dictTree.DictTree;
import com.example.bookplaza.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Update preference values
 * @author Yucheng Zhu
 */
public class MeanPreferencesValuesMapUpdater {

    // load TF-IDF map from the file
    TfIdf tfIdf;

    // load preference values from the file
    private DictTree<Integer, Double> preferenceValues;
    private HashMapReader<HashMap<Integer, Float>> preferenceValuesReader = new HashMapReader();

    /**
     * Construct the instance variables in the Android context
     * Reads the preference values
     * @author Yucheng Zhu
     * @throws IOException preferenceValues file not found.
     */
    public MeanPreferencesValuesMapUpdater(Context context) throws IOException {

        // construct TF-IDF matrix
        if (context == null) tfIdf = new TfIdf();
        else tfIdf = new TfIdf(context);

        // read preference values
        InputStream is;
        if (context == null) { // test context
            is = Reader.assetsFolder("preferenceValues.json");
        } else { // Android context
            is = Reader.assetsFolder(context, "preferenceValues.json");
        }
        HashMap<Integer, Double> preferenceValuesDict =
                preferenceValuesReader.readPreferenceValues(
//                        Reader.assetsFolder(context, "preferenceValues.json")
                        is
                );

        // set preferenceValues
        preferenceValues = new DictTree<>(preferenceValuesDict);

        // set the second and third groups' first books' ids
        ArrayList<Integer> memorisedKeys = new ArrayList<>(2);
        memorisedKeys.add(1073); // first book id for HotFeed
        memorisedKeys.add(2548); // first book id for Explore
        preferenceValues.setMemorisedKeys(memorisedKeys);
    }

    /**
     * Get the TF-IDF map
     * @author Yucheng Zhu
     * @return TF-IDF map.
     */
    public TfIdf getTfIdf() {
        return tfIdf;
    }

    /**
     * Get the preference values map
     * @author Yucheng Zhu
     * @return Preference values map.
     */
    public DictTree<Integer, Double> getPreferenceValues() {
        return preferenceValues;
    }

    /**
     * Get the book's genres from file
     *
     * @author Yucheng Zhu
     * @param clickedBookId The index for the book the user clicked into
     * @return Current book's genres
     * @throws IOException File cannot be read or stream is closed
     */
    public HashSet<Integer> getBookGenres(int clickedBookId) throws IOException {
        return getBookGenres(null, clickedBookId);
    }

    /**
     * Get the book's genres from file
     *
     * @author Yucheng Zhu
     * @param clickedBookId The index for the book the user clicked into
     * @return Current book's genres
     * @throws IOException File cannot be read or stream is closed
     */
    public HashSet<Integer> getBookGenres(Context context, int clickedBookId) throws IOException {

        String genresString =
                Reader.read(Reader.assetsFolder(context, "genresTokenised/"+clickedBookId));

        return IndicesReader.toIndices(genresString);
    }

    /**
     * Update mean preference values when the user clicks a book
     * @author Yucheng Zhu
     * @param clickedBookId The id for the book the user clicked
     * @throws IOException Cannot find file for the genresTokenised
     */
    public HashSet<Integer> updateMeanPreferenceValues(Context context, int clickedBookId) {
        // books that changed ids
        HashSet<Integer> changedBooksIds = new HashSet<>();

        // all genres of the current book
        HashSet<Integer> bookGenres;

        if (context != null) {
            try {
                if (clickedBookId >= 0 && clickedBookId < Data.BOOKS_COUNT) {
                    bookGenres = getBookGenres(context, clickedBookId);
                } else {
                    bookGenres = getBookGenres(context, 0);
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        } else {
            try {
                bookGenres = getBookGenres(clickedBookId);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        // each book can only contribute to at most 3 genres so that some books won't have too much influence
        int genresPerBookCount = 0;
        final int MAX_GENRES_PER_BOOK = 3;
        // each genre can only contribute to at most 3 books so that some genres won't have too much influence
        int booksPerGenreCount = 0;
        final int MAX_BOOKs_PER_GENRE = 3;

        // update all genres this book has
        for (int bookGenreTokenised: bookGenres) {
            HashMap<Integer, Double> booksWithAGenre = tfIdf.getTfIdfMap().get(bookGenreTokenised);

            // ensure non-null
            if (booksWithAGenre == null) continue;
            // update all books' preference values
            for (Integer bookId: booksWithAGenre.keySet()) {
                // ensure non-null
                if (preferenceValues.get(bookId) == null) continue;
                if (booksWithAGenre.get(bookId) == null) continue;

                // add to the preference values
                preferenceValues.updateValue(
                        bookId,
                        preferenceValues.get(bookId) + booksWithAGenre.get(bookId)
                );

                // add the book into changed books id
                changedBooksIds.add(bookId);

                // each genre can only contribute to at most 3 books so that some genres won't have too much influence
                booksPerGenreCount++;
                if (booksPerGenreCount >= MAX_BOOKs_PER_GENRE) {
                    break;
                }
            }

            // each book can only contribute to at most 3 genres so that some books won't have too much influence
            genresPerBookCount++;
            if (genresPerBookCount >= MAX_GENRES_PER_BOOK) {
                break;
            }
        }

        return changedBooksIds;
    }
}
