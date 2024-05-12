package com.example.bookplaza.surprise;

import android.content.Context;
import com.example.bookplaza.io.HashMapReader;
import com.example.bookplaza.io.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Term frequency - inverse document frequency (TF-IDF) calculator
 *
 * @author Yucheng Zhu
 */
public class TfIdf {
    HashMapReader<HashMap<Integer, HashMap<Integer, Double>>> hashMapReader = new HashMapReader<>();
    // TF-IDF table to look up
    private final HashMap<Integer, HashMap<Integer, Double>> tfIdfMap;

    /**
     * A constructor which sets tfIdfMap by reading its value from the file
     * @author Yucheng Zhu
     * @throws IOException TF-IDF file not found
     */
    public TfIdf() throws IOException {
        this.tfIdfMap = readTfIdf();
    }

    /**
     * A constructor which sets tfIdfMap by reading its value from the file in the Android context
     * @author Yucheng Zhu
     * @throws IOException TF-IDF file not found
     */
    public TfIdf(Context context) throws IOException {
        this.tfIdfMap = readTfIdf(context);
    }

    /**
     * Get TF-IDF map
     * @author Yucheng Zhu
     * @return TF-IDF map
     */
    public HashMap<Integer, HashMap<Integer, Double>> getTfIdfMap() {
        return tfIdfMap;
    }

    /**
     * Get TF-IDF map from file, and save it to the instance variable
     * @author Yucheng Zhu
     * @return The TF-IDF map
     */
    public HashMap<Integer, HashMap<Integer, Double>> readTfIdf() throws IOException {
        InputStream is = Reader.assetsFolder("tfIdf.json");
        return hashMapReader.readTfIdfMap(is);
    }

    /**
     * Get TF-IDF map from file, and save it to the instance variable
     * @author Yucheng Zhu
     * @return The TF-IDF map
     */
    public HashMap<Integer, HashMap<Integer, Double>> readTfIdf(Context context) throws IOException {
        InputStream is = Reader.assetsFolder(context, "tfIdf.json");
        return hashMapReader.readTfIdfMap(is);
    }

    /**
     * Get stored TF-IDF for the given term and document
     * @author Yucheng Zhu
     * @param term The given term
     * @param doc The document
     * @return The TF-IDF value for the given term and document
     */
    public static Double getTfIdf(
            HashMap<Integer, HashMap<Integer, Double>> tfIdfMap,
            int term,
            Integer doc
    ) {
        return tfIdfMap.getOrDefault(term, new HashMap<>()).get(doc);
    }

    /**
     * Get stored TF-IDF for the given term and document in the current object
     * @author Yucheng Zhu
     * @param term The given term
     * @param doc The document
     * @return The TF-IDF value for the given term and document
     */
    public Double getTfIdf(
            int term,
            Integer doc
    ) {
        return tfIdfMap.getOrDefault(term, new HashMap<>()).get(doc);
    }
}
