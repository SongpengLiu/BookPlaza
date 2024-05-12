package com.example.bookplaza.io;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Read written hash map
 * @author Yucheng Zhu
 */
public class HashMapReader <T> {
    /**
     * Read written hash map
     * @author Yucheng Zhu
     */
    public T read(
            InputStream inputStream,
            Type type
    ) throws IOException {
        Gson gson = new Gson();

        // get file content
        String json = Reader.read(inputStream);

        // Parse String to HashMap
        return gson.fromJson(json, type);
    }

    /**
     * Read written hash map for preference values
     * @author Yucheng Zhu
     * @param inputStream File input stream
     * @return Preference values
     */
    public HashMap<Integer, Double> readPreferenceValues(
            InputStream inputStream
    ) throws IOException {
        return (HashMap<Integer, Double>) read(
                inputStream,
                new TypeToken<HashMap<Integer, Double>>(){}.getType()
        );
    }

    /**
     * Read written hash map for TF-IDF
     * @author Yucheng Zhu
     * @param inputStream File input stream
     * @return TF-IDF map
     */
    public HashMap<Integer, HashMap<Integer, Double>> readTfIdfMap(
            InputStream inputStream
    ) throws IOException {
        return (HashMap<Integer, HashMap<Integer, Double>>) read(
                inputStream,
                new TypeToken<HashMap<Integer, HashMap<Integer, Double>>>(){}.getType()
        );
    }

    /**
     * Read written hash map for TF-IDF
     * @author Yucheng Zhu
     * @param inputStream File input stream
     * @return TF-IDF map
     */
    public HashSet<ArrayList<Integer>> readInfoForBooksChangedGroups(
            InputStream inputStream
    ) throws IOException {
        return (HashSet<ArrayList<Integer>>) read(
                inputStream,
                new TypeToken<HashSet<ArrayList<Integer>>>(){}.getType()
        );
    }
}
