package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;
import com.example.bookplaza.trees.TreeResultOperator;

import java.util.ArrayList;
import java.util.Map;

public class EquateExp<D extends Comparable<D>> extends Exp {

    Exp coefficient;
    D value;

    public EquateExp(Exp coefficient, D value) {
        this.coefficient = coefficient;
        this.value = value;

    }

    @Override
    public String show() {
        return coefficient.show() + "=" + value;
    }

    @Override
    public BST<Integer> evaluate() {
        return null;
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        return coefficient.evaluate().between(value, value, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
    }
}
