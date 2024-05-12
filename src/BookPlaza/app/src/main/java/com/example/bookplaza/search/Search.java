package com.example.bookplaza.search;

import com.example.bookplaza.trees.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Search engine for retrieving relevant Trees for each attribute and
 * relevant arraylist for genre and title
 *
 * @author Shao An Tay(u7553225)
 */
public class Search {
    static AVLTree<?> defaultReviews = null;
    static AVLTree<?> defaultRating = null;
    static AVLTree<?> defaultPrice = null;
    static ArrayList<TreeData<Integer>> defaultGenre = null;
    static ArrayList<TreeData<Integer>> defaultTitle = null;


    /**
     * Loads a default tree into Search that will be returned if attributes is null.
     * For testing purposes
     *
     * @author Shao An Tay(u7553225)
     */
    public static void loadTree(Token.Type tree, AVLTree<?> defaultTree) {
        if (tree == Token.Type.REVIEWS) {
            defaultReviews = defaultTree;
        }
        else if (tree == Token.Type.RATING) {
            defaultRating = defaultTree;
        }
        else if (tree == Token.Type.PRICE) {
            defaultPrice = defaultTree;
        }
    }

    /**
     * Loads a default arraylist into Search that will be returned if attributes is null.
     * For testing purposes
     *
     * @author Shao An Tay(u7553225)
     */
    public static void loadHashMap(Token.Type tree, ArrayList<TreeData<Integer>> defaultAL) {
        if (tree == Token.Type.GENRE) {
            defaultGenre = defaultAL;
        }
        else if (tree == Token.Type.CONTAINS) {
            defaultTitle = defaultAL;
        }
    }

    /**
     * Return the tree for a certain attribute
     *
     * @author Shao An Tay(u7553225)
     */
    public static AVLTree<?> search(Token.Type tree, BooksAttributes attributes) {
        if (attributes == null) {
            System.out.println("null: " + defaultRating);
            if (tree == Token.Type.REVIEWS) return defaultReviews;
            else if (tree == Token.Type.RATING) return defaultRating;
            else if (tree == Token.Type.PRICE) return defaultPrice;
        }

        if (tree == Token.Type.REVIEWS) return attributes.getRatersCounts();
        else if (tree == Token.Type.RATING) return attributes.getRatings();
        else if (tree == Token.Type.PRICE) return attributes.getBestPrices();
        return null;
    }

    /**
     * Return arraylist for book titles that match a bookname
     *
     * @author Shao An Tay(u7553225)
     */
    public static ArrayList<TreeData<Integer>> searchBook(String bookname, BooksAttributes attributes) {
        if (attributes == null) return defaultTitle;

        HashMap<String, ArrayList<TreeData<Integer>>> titlesHashMap = attributes.getTitles();

        String[] tokens = bookname.split("\"");
        bookname = tokens[1].substring(0, tokens[1].length());
        ArrayList<TreeData<Integer>> titles = titlesHashMap.get(bookname);
        return titles;
    }

    /**
     * Return arraylist for genres that match a string
     *
     * @author Shao An Tay(u7553225)
     */
    public static ArrayList<TreeData<Integer>> searchGenre(String genre, BooksAttributes attributes) {
        if (attributes == null) return defaultGenre;

        HashMap<String, ArrayList<TreeData<Integer>>> genreHashMap = attributes.getGenres();

        String[] tokens = genre.split("\"");
        genre = tokens[1].substring(0, tokens[1].length());
        ArrayList<TreeData<Integer>> genres = genreHashMap.get(genre);
        return genres;
    }

}

