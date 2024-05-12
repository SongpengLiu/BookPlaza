package com.example.bookplaza.io;

import android.content.Context;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * Methods to read books' indices
 * @author Yucheng Zhu
 */
public class IndicesReader {
    /**
     * Convert all the books' indices as a string to a set of indices
     *
     * @author Yucheng Zhu
     * @param indicesString The current books' group's indices as a string (e.g. "0,1,2")
     * @return All the books' indices in the current book's group as a set
     */
    public static Set<Integer> toIndices(String indicesString, boolean useTreeSet){
        // empty indices
        if (indicesString.isEmpty()) return new HashSet<>();

        // parse the string to a set of indices
        String[] indicesStringsArray = indicesString.split(",");

        // set initial capacity to avoid table doubling
        Set<Integer> indices = useTreeSet ?
                new TreeSet<>() :
                new HashSet<>(2 * indicesStringsArray.length);

        for (String index : indicesStringsArray) {
            indices.add(Integer.valueOf(index));
        }

        // return the indices
        return indices;
    }

    /**
     * Convert all the books' indices as a string to a set of indices
     *
     * @author Yucheng Zhu
     * @param indicesString The current books' group's indices as a string (e.g. "0,1,2")
     * @return All the books' indices in the current book's group as a set
     */
    public static HashSet<Integer> toIndices(String indicesString) {
        return (HashSet<Integer>) toIndices(indicesString, false);
    }

    /**
     * Read all the books' indices from file as a set of indices
     *
     * @author Yucheng Zhu
     * @param inputStream The file's inputStream
     * @return All the books' indices in the current book's group as a set
     */
    public static HashSet<Integer> read(InputStream inputStream) throws IOException {
        String fileContent = Reader.read(inputStream);
        return toIndices(fileContent);
    }
}
