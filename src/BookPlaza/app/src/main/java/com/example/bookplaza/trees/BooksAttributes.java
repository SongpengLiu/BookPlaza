package com.example.bookplaza.trees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Objects;

/**
 * Put all books attributes into the optimal data structure
 */
public class BooksAttributes implements Serializable {
    // Declare all fields
    private final HashMap<String, ArrayList<TreeData<Integer>>> authors = new HashMap<>();
    private final HashMap<String, ArrayList<TreeData<Integer>>> titles = new HashMap<>();
    private final AVLTree<Float> ratings = new AVLTree<>(new EmptyNode<Float>());
    private final AVLTree<Integer> ratersCounts = new AVLTree<>(new EmptyNode<Integer>());
    private final HashMap<String, ArrayList<TreeData<Integer>>> genres = new HashMap<>();
    private final AVLTree<Float> bestPrices = new AVLTree<>(new EmptyNode<Float>());
    private final HashMap<String, ArrayList<TreeData<Integer>>> sellers = new HashMap<>();
    private final HashMap<String, ArrayList<TreeData<Integer>>> urls = new HashMap<>();

    /**
     * Add a book's attribute to a HashMap
     * @author Yucheng Zhu
     * @param dict The HashMap to store the book's attribute and ids with that attributes
     *             e.g. If books with id=2,3,5 all have the same title "Pride and Prejudice"
     *             Then it is {"Pride and Prejudice": [2, 3, 5]}
     * @param key The book's attribute
     * @param value The id to add
     */
    public static void addBookToDict(
            HashMap<String, ArrayList<TreeData<Integer>>> dict, String key, int value) {
        // create an empty list if the key doesn't already exist
        if (!dict.containsKey(key)) {
            dict.put(key, new ArrayList<>());
        }

        // add the value
        Objects.requireNonNull(
                dict.get(key)
        ).add(new TreeData<>(null, value));
    }

    /**
     * Remove a book's attribute from a HashMap
     * @author Yucheng Zhu
     * @param dict The HashMap to store the book's attribute and ids with that attributes
     *             e.g. If books with id=2,3,5 all have the same title "Pride and Prejudice"
     *             Then it is {"Pride and Prejudice": [2, 3, 5]}
     * @param key The book's attribute
     * @param value The id to add
     */
    public static void removeBookFromDict(
            HashMap<String, ArrayList<TreeData<Integer>>> dict, String key, int value) {
        // Exception: the key doesn't exist

        // add the value
        System.out.println("BooksAttributes.removeBookFromDict(): dict="+dict);
        System.out.println("BooksAttributes.removeBookFromDict(): key="+key);
        System.out.println("BooksAttributes.removeBookFromDict(): dict.containsKey(key)="+dict.containsKey(key));
        if (dict.containsKey(key)) {
            Objects.requireNonNull(
                    dict.get(key)
            ).removeIf(treeData -> treeData.getId() == value); // values have few duplicates if any

            // delete an empty list if there are no keys in it
            if (Objects.requireNonNull(dict.get(key)).isEmpty()) {
                dict.remove(key);
            }
        }
    }

    // Getters for all attributes
    public HashMap<String, ArrayList<TreeData<Integer>>> getAuthors() {
        return authors;
    }

    public HashMap<String, ArrayList<TreeData<Integer>>> getTitles() {
        return titles;
    }

    public AVLTree<Float> getRatings() {
        return ratings;
    }

    public AVLTree<Integer> getRatersCounts() {
        return ratersCounts;
    }

    public HashMap<String, ArrayList<TreeData<Integer>>> getGenres() {
        return genres;
    }

    public AVLTree<Float> getBestPrices() {
        return bestPrices;
    }

    public HashMap<String, ArrayList<TreeData<Integer>>> getSellers() {
        return sellers;
    }

    public HashMap<String, ArrayList<TreeData<Integer>>> getUrls() {
        return urls;
    }
}
