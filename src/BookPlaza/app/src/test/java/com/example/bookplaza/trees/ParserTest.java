package com.example.bookplaza.trees;

import com.example.bookplaza.search.Exp;
import com.example.bookplaza.search.Parser;
import com.example.bookplaza.search.Tokenizer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    @DisplayName("tests for basic value of = > and <")
    public void basicTest() {
        String[] queries = {
                "rating = 5", "reviews = 5", "genre\"horror\"", "price = 1.11",
                "rating > 5.1", "reviews > 5", "title\"horror\"", "price > 5",
                "rating < 5", "reviews < 5", "genre\"horror\"", "price < 3.3333",

        };
        String[] results = {
                "RATING=5", "REVIEWS=5", "(genre\"horror\")", "PRICE=1.11",
                "RATING>5.1", "REVIEWS>5", "(title\"horror\")", "PRICE>5",
                "RATING<5", "REVIEWS<5", "(genre\"horror\")", "PRICE<3.3333",

        };
        for (int k = 0; k < queries.length; k++) {
            String input = queries[k];
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp(null);
            assertEquals(expression.show(), results[k]);
        }
    }

    @Test
    @DisplayName("tests for union operator")
    public void unionTest() {
        String[] queries = {
                "rating = 5 | price = 3",  "reviews = 1.2 | price > 3", "reviews = 5 | price > 3 | rating = 7.333",
                "(rating = 5) | price = 3", "genre\"Horror\" | title\"Horror\""
        };
        String[] results = {
                "(RATING=5 | PRICE=3)", "(REVIEWS=1.2 | PRICE>3)", "(REVIEWS=5 | (PRICE>3 | RATING=7.333))",
                "(RATING=5 | PRICE=3)", "((genre\"Horror\") | (title\"Horror\"))"

        };
        for (int k = 0; k < queries.length; k++) {
            String input = queries[k];
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp(null);
            assertEquals(expression.show(), results[k]);
        }
    }

    @Test
    @DisplayName("tests for intsection operator")
    public void intersecTest() {
        String[] queries = {
                "rating = 5 & price = 3",  "reviews = 5 & price > 3", "reviews = 5 & price > 3 & rating = 7",
                "(rating = 5) & price = 3"
        };
        String[] results = {
                "(RATING=5 & PRICE=3)", "(REVIEWS=5 & PRICE>3)", "(REVIEWS=5 & (PRICE>3 & RATING=7))",
                "(RATING=5 & PRICE=3)"

        };
        for (int k = 0; k < queries.length; k++) {
            String input = queries[k];
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp(null);
            assertEquals(expression.show(), results[k]);
        }
    }

    @Test
    @DisplayName("tests for advanced queries")
    public void advTest() {
        String[] queries = {
                "rating = 5 & price > 4 | (rating = 3 | (reviews = 100 & rating < 50))",
                "(((((reviews = 100 & rating < 50)))))",
                "reviews = 100 & rating < 50 | reviews = 50 & rating <10 | reviews = 22 & rating < 10"

        };
        String[] results = {
                "((RATING=5 & PRICE>4) | (RATING=3 | (REVIEWS=100 & RATING<50)))",
                "(REVIEWS=100 & RATING<50)",
                "((REVIEWS=100 & RATING<50) | ((REVIEWS=50 & RATING<10) | (REVIEWS=22 & RATING<10)))"

        };
        for (int k = 0; k < queries.length; k++) {
            String input = queries[k];
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp(null);
            assertEquals(expression.show(), results[k]);
        }
    }

    @Test
    @DisplayName("tests for bad queries")
    public void failTest() {
        String input = "reviews = 5 = 5 & rating = 4";
        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp(null);
        System.out.println(expression.show());

    }
}
