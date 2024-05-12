package com.example.bookplaza.search;


import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class QueryExp extends Exp {
    private Token.Type type;

    private BooksAttributes attributes;

    public QueryExp(Token.Type type, BooksAttributes attributes) {
        this.attributes = attributes;
        this.type = type;
    }

    @Override
    public String show() {
        return type.name();
    }

    // returns tree for a particular variable
    @Override
    public BST evaluate() {
        return Search.search(type, attributes);
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        return null;
    }
}
