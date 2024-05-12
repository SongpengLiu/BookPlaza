package com.example.bookplaza.surprise;

import android.content.Context;
import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.io.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Read tokenised genres
 * @author Yucheng Zhu
 */
public class TokenisedGenresReader {
    // TF-IDF computation object
    Data data = Data.getInstance();

    public TokenisedGenresReader() throws IOException {
    }

    /**
     * Read genres' files to a matrix for TF-IDF analysis
     * @author Yucheng Zhu
     * @return The documents matrix
     * @throws IOException File not found or closed
     */
    public ArrayList<HashSet<Integer>> readTokenisedGenres() throws IOException {
        ArrayList<HashSet<Integer>> docs = new ArrayList<>();

        for (int i = 0; i < data.BOOKS_COUNT; i++) {
            String fileName = String.valueOf(i);

            // read file to indices
            InputStream inputStream = Reader.assetsFolder(
                    "genresTokenised/" + i
            );

            String s = Reader.read(inputStream);
            HashSet<Integer> doc = IndicesReader.toIndices(s);

            docs.add(doc);
        }

        return docs;
    }
}
