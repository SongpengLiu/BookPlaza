package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;

public class GenreExp extends Exp {
    private String str;

    public GenreExp(String str) {
        this.str = str;
    }

    @Override
    public String show() {
        return "(" + str + ")";
    }

    @Override
    public BST evaluate() {
        return null;
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        return Search.searchGenre(str, attributes);
    }
}
