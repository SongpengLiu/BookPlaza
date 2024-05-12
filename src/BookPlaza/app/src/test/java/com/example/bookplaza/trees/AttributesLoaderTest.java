package com.example.bookplaza.trees;

import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.Reader;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test AttributesLoader
 * @author Yucheng Zhu
 */
public class AttributesLoaderTest<D extends Comparable<D>> {
    // Main test object
    BooksAttributes attributes;

    /**
     * Load all attributes into the optimal data structure so that they can be sorted and searched in O(1) or o(log n).
     * @author Yucheng Zhu
     */
    @Before
    public void init() throws IOException {
        AttributesLoader attributesLoader = new AttributesLoader();

        attributes = attributesLoader.readBooksToAttributes(
                Reader.assetsFolder(Data.BOOK_CSV_FILE), AttributesLoader.ALL_INDICES
        );
    }

    /**
     * Test authors are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetAuthors() {
        // get authors HashMap
        HashMap<String, ArrayList<TreeData<Integer>>> authorsHashMap = attributes.getAuthors();

        //
        assertTrue(authorsHashMap.containsKey("Jamie McGuire"));

        // check the first author
        assertEquals(new TreeData<>(null, 0), authorsHashMap.get("Jamie McGuire").get(0));

        // check the second author
        assertEquals(new TreeData<>(null, 1), authorsHashMap.get("E.L. James").get(0));
    }

    /**
     * Test titles are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetTitles() {
        // get titles' HashMap
        HashMap<String, ArrayList<TreeData<Integer>>> titlesHashMap = attributes.getTitles();

        // check the title is in it
        assertTrue(titlesHashMap.containsKey("Beautiful Disaster"));

        // check the first title
        ArrayList<TreeData<Integer>> title1Ids = new ArrayList<>();
        title1Ids.add(new TreeData<>(null, 0));
        assertEquals(title1Ids, titlesHashMap.get("Beautiful Disaster"));
    }

    /**
     * Test ratings are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetRatings() {
        // get ratings HashMap
        AVLTree<Float> ratingsTree = attributes.getRatings();

        // Test finding a node works
        assertEquals(
                new TreeData<>(4.02F, 0),
                ratingsTree.find(new TreeData<>(4.02F, 0)).getContent()
        );

        // IMPORTANT, make sure you don't look for an exact value for FLOAT values due to how it's represented in Java
        assertEquals( // find the closest larger
                new TreeData<>(4.0F, 116),
                ratingsTree.find(4.0F, TreeResultOperator.EQUAL_OR_LARGER).getContent()
        );

        // find successor
        assertEquals(
                new TreeData<>(4.0F, 129),
                ratingsTree.find(4.0F, TreeResultOperator.EQUAL_OR_LARGER)
                        .successor().getContent()
        );

        // find predecessor
        assertEquals(
                new TreeData<>(3.99F, 2854),
                ratingsTree.find(4.0F, TreeResultOperator.EQUAL_OR_LARGER)
                        .predecessor().getContent()
        );

        // find ratings in between
        assertEquals(
                new TreeData<>(4.1F, 64),
                ratingsTree.between(
                        4.1F, 4.2F,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                ).get(0)
        );
    }

    /**
     * Test raters' counts are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetRatersCounts() {
        // get raters' counts HashMap
        AVLTree<Integer> ratersCountsTree = attributes.getRatersCounts();

        // Test finding a node works
        assertEquals(
                new TreeData<>(666487, 0),
                ratersCountsTree.find(new TreeData<>(666487, 0)).getContent()
        );

        assertEquals( // find the closest larger
                new TreeData<>(666487, 0),
                ratersCountsTree.find(666487, TreeResultOperator.EQUAL_OR_LARGER).getContent()
        );

        assertEquals( // find the closest larger
                new TreeData<>(666487, 0),
                ratersCountsTree.find(666487).getContent()
        );

        // find successor
        assertEquals(
                new TreeData<>(2518514, 825),
                ratersCountsTree.find(2491856, TreeResultOperator.EQUAL_OR_LARGER)
                        .successor().getContent()
        );

        // find predecessor
        assertEquals(
                new TreeData<>(515886, 2466),
                ratersCountsTree.find(516935, TreeResultOperator.EQUAL_OR_LARGER)
                        .predecessor().getContent()
        );

        // find raters' counts in between
        assertEquals(
                Arrays.asList(new TreeData[]{
//                        new TreeData<>(226462, 7),
                        new TreeData<>(201674, 557),
                }),
                ratersCountsTree.between(
                        200_000, 200_100,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }

    /**
     * Test genres are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetGenres() {
        // get genres HashMap
        HashMap<String, ArrayList<TreeData<Integer>>> genresHashMap = attributes.getGenres();

        //
        assertTrue(genresHashMap.containsKey("Romance"));

        // check the first title
        assertEquals(new TreeData<>(null, 0), genresHashMap.get("Romance").get(0));
    }

    /**
     * Test best price are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetBestPrices() {
        // get best prices' tree
        AVLTree<Float> bestPricesTree = attributes.getBestPrices();

        // find the best prices in between
        assertEquals(
                new TreeData<>(5.03F, 226),
                bestPricesTree.between(
                        3.0F, 6.0F,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                ).get(0)
        );
    }

    /**
     * Test sellers are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetSellers() {
        // get sellers HashMap
        HashMap<String, ArrayList<TreeData<Integer>>> sellersHashMap = attributes.getSellers();

        //
        assertTrue(sellersHashMap.containsKey("Amazon"));

        // check the first title
        ArrayList<TreeData<Integer>> seller1Ids = new ArrayList<>();
        assertEquals(new TreeData<>(null, 0), sellersHashMap.get("Amazon").get(0));
    }

    /**
     * Test URLs are loaded correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testGetUrls() {
        // get urls HashMap
        HashMap<String, ArrayList<TreeData<Integer>>> urlsHashMap = attributes.getUrls();

        //
        assertTrue(urlsHashMap.containsKey("https://www.amazon.com/s?k=Jamie+McGuire+Beautiful+Disaster"));

        // check the first title
        ArrayList<TreeData<Integer>> url1Ids = new ArrayList<>();
        url1Ids.add(new TreeData<>(null, 0));
        assertEquals(url1Ids, urlsHashMap.get("https://www.amazon.com/s?k=Jamie+McGuire+Beautiful+Disaster"));
    }
}
