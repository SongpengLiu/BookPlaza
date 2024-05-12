package com.example.bookplaza.io;

import android.content.Context;
import com.example.bookplaza.dictTree.NumbersPair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Define which books data to use
 * This class is stateless and idempotent. It faithfully translates the current situations of the books' data.
 * @author Yucheng Zhu
 */
public class Data {
    // The variables should NEVER be changed when the app runs
    public final static String BOOK_CSV_FILE = "test_books_list_3000_realistic.csv";

    // Singleton Pattern guarantees that the expensive computation is only done once and never changed
    public static final int BOOKS_COUNT = 2900;

    public final static int MAX_GROUP_COUNT = 3;

    private static Data instance = null;

    private Data() {}

    /**
     * Data uses the Singleton pattern
     * Its variables are final to avoid change
     * Its methods are stateless to avoid duplicate copies of the same variables
     * @return THE data object
     * @throws IOException
     */
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    /**
     * Count the number of lines in a string
     * @author Yucheng Zhu
     * @param fileContent String to count the number of lines
     * @return Line number count
     */
    public static int countLines(String fileContent) {
        int newlines = 1; // no new line means one line
        for (int i = 0; i < fileContent.length(); i++) {
            if (fileContent.charAt(i) == '\n') { // each new line adds a line
                newlines++;
            }
        }
        return newlines;
    }

    /**
     * Tripartite an int into 3 parts
     * For example, 2900 becomes 966, 966 and 968.
     * @author Yucheng Zhu
     * @param number Number of partitions
     * @return Tripartited integer numbers
     */
    private static int[] tripartiteInt(int number) {
        int part1 = number / MAX_GROUP_COUNT;
        int part2 = part1; // same as first
        int part3 = number - (part1 + part2); // remaining
        return new int[] {part1, part2, part3};
    }

    /**
     * Tripartite an int into 3 parts
     *      * For example, 2900 becomes 966, 966 and 968.
     * @author Yucheng Zhu
     * @return Tripartited books' groups sizes' count
     */
    public int[] tripartiteBooksGroups() {
        return tripartiteInt(BOOKS_COUNT);
    }

    /**
     * A general method for the first and last index of the n-th partition.
     * O(1)
     * @author Yucheng Zhu
     * @param totalFiles all files count
     * @param partitionNumber Current partition count
     * @return Pair of beginning and end indices for the current partition
     */
    private static NumbersPair<Integer, Integer> calculateTripartiteIndices(int totalFiles, int partitionNumber) {
        int partSize = totalFiles / MAX_GROUP_COUNT;

        int firstIndex = partitionNumber * partSize;
        int lastIndex = firstIndex + partSize - 1;
        if (partitionNumber == MAX_GROUP_COUNT) {
            lastIndex += totalFiles % MAX_GROUP_COUNT; // add the remainder to the last partition
        }

        // last group to the last index
        if (partitionNumber == MAX_GROUP_COUNT - 1) {
            lastIndex = totalFiles - 1;
        }

        return new NumbersPair<>(firstIndex, lastIndex);
    }

    /**
     * A general method for the first and last index of the n-th book group.
     * O(1)
     * @author Yucheng Zhu
     * @param partitionNumber Current partition count
     * @return Pair of beginning and end indices for the current book group
     */
    public NumbersPair<Integer, Integer> calculateTripartiteIndices(int partitionNumber) {
        return calculateTripartiteIndices(BOOKS_COUNT, partitionNumber);
    }

    /**
     * Given a number, check which group it is in (the groups are 0, 1 and 2 respectively)
     * @author Yucheng Zhu
     * @param bookId Current book's index
     * @return The Group number
     */
    public int findGroup(int bookId) {
        // check illegal index
        if (bookId >= BOOKS_COUNT || bookId < 0) {
            throw new IllegalArgumentException(bookId + " is not in any group");
        }

        // compute the book's group number
        int partSize = BOOKS_COUNT / MAX_GROUP_COUNT;

        if (bookId < partSize) {
            return 0;
        } else if (bookId < 2 * partSize) {
            return 1;
        } else {
            return 2;
        }
    }
}
