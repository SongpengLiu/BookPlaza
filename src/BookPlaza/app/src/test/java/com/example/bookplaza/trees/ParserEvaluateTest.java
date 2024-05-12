package com.example.bookplaza.trees;

import com.example.bookplaza.search.*;
import com.example.bookplaza.searchResults.SearchResultsHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.Intent.getIntent;
import static org.junit.Assert.*;

public class ParserEvaluateTest {
    // -- The trees to test
    // An empty tree
    AVLTree<Integer> newAVL;
    // An inserted tree
    AVLTree<Float> normalAVL;
    // A pathological case
    AVLTree<Integer> sortedAVL;

    // -- A reference to the root
    AVLNode<Integer> newAVLRoot;

    Integer[] initializeSorted;

    public boolean containsVAL(ArrayList<TreeData<Integer>> result , Integer comp) {
        for (TreeData<Integer> k: result) {
            System.out.println(k.getComparable() + "-" + comp);
            if (Objects.equals(k.getComparable(), comp)) {
                return true;
            }
        }
        return false;
    }

    @Before
    public void init() {
        initializeSorted = new Integer[]{100, 200, 3000, 400, 5000, 1000, 2000, 0, 10000};
        // Create a filled tree
        normalAVL = new AVLTree<>(new EmptyNode<Float>());
        normalAVL.insert(new TreeData<>(1.1f, 1));
        normalAVL.insert(new TreeData<>(3.22f, 2));
        normalAVL.insert(new TreeData<>(1.4f, 3));
        normalAVL.insert(new TreeData<>(5.0f, 4));
        normalAVL.insert(new TreeData<>(4.11f, 5));
        normalAVL.insert(new TreeData<>(2.33f, 6));
        normalAVL.insert(new TreeData<>(0.0f, 7));
        normalAVL.insert(new TreeData<>(0.5f, 8));
        normalAVL.insert(new TreeData<>(4.44f, 9));

        // Create a pathological case
        sortedAVL = new AVLTree<>(new EmptyNode<Integer>());

        int i = 0;
        for (Integer k: initializeSorted) {
            sortedAVL.insert(new TreeData<>(k, i));
            i += 1;
        }

        Search.loadTree(Token.Type.RATING, normalAVL);
        Search.loadTree(Token.Type.REVIEWS, sortedAVL);
    }
    @Test
    @DisplayName("tests for basic value of = > and <")
    public void basicTest() {
        Tokenizer tokenizer = new Tokenizer("reviews > 0 ");
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp(null);
        ArrayList<TreeData<Integer>> treeDataList = expression.evaluateBooks(null);
        for (Integer k: initializeSorted) {
            assertTrue(containsVAL(treeDataList, k));
        }
        Integer[] answer1 = new Integer[]{100, 200, 3000, 400, 5000, 1000, 2000, 10000};
        tokenizer = new Tokenizer("reviews > 100");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
        answer1 = new Integer[]{100, 200, 3000, 400, 5000, 1000, 2000};
        tokenizer = new Tokenizer("reviews < 5000");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
        answer1 = new Integer[]{100};
        tokenizer = new Tokenizer("reviews = 100");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }

        answer1 = new Integer[]{100};
        tokenizer = new Tokenizer("(reviews = 100)");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
    }

    @Test
    @DisplayName("union and intersection operators")
    public void unionAndIntersect() {
        Tokenizer tokenizer = new Tokenizer("reviews > 0 ");
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp(null);
        ArrayList<TreeData<Integer>> treeDataList = expression.evaluateBooks(null);
        for (Integer k: initializeSorted) {
            assertTrue(containsVAL(treeDataList, k));
        }
        Integer[] answer1 = new Integer[]{100, 200};
        tokenizer = new Tokenizer("reviews = 100 | reviews = 200");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
        answer1 = new Integer[]{100, 200};
        tokenizer = new Tokenizer("reviews > 100 & reviews < 250");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
        answer1 = new Integer[]{100, 200, 400};
        tokenizer = new Tokenizer("reviews = 100 | (reviews = 200 | reviews = 400)");
        parser = new Parser(tokenizer);
        expression = parser.parseExp(null);
        treeDataList = expression.evaluateBooks(null);
        for (Integer k: answer1) {
            assertTrue(containsVAL(treeDataList, k));
        }
    }
}
