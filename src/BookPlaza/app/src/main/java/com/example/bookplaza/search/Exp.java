package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;

public abstract class Exp {

    public abstract String show();
    public abstract BST evaluate();
    public abstract ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes);
}
