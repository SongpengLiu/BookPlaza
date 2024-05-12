package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;

public class ContainsExp extends Exp {

    private String str;



    public ContainsExp(String str) {
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
        return Search.searchBook(str, attributes);
    }
}
