package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;
import com.example.bookplaza.trees.TreeResultOperator;

import java.util.ArrayList;
import java.util.Map;

public class LessThanExp<D extends Comparable<D>> extends Exp {

    Exp coefficient;
    D value;

    public LessThanExp(Exp coefficient, D value) {
        this.coefficient = coefficient;
        this.value = value;

    }

    @Override
    public String show() {
        return coefficient.show() + "<" + value;
    }

    @Override
    public BST<Integer> evaluate() {
        return null;
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        if (coefficient.evaluate().getRoot().isEmpty(coefficient.evaluate().getRoot())) {
            return new ArrayList<>();
        }

        if (coefficient.evaluate().getRoot().getContent().getComparable() instanceof Integer) {
            if (value.getClass() == Float.class) {
                return coefficient.evaluate().between(Integer.MIN_VALUE,((Float)value).intValue(), TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
            }
            return coefficient.evaluate().between(Integer.MIN_VALUE, value, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
        } else if (coefficient.evaluate().getRoot().getContent().getComparable() instanceof Float) {
            if (value.getClass() == Integer.class) {
                return coefficient.evaluate().between(Float.MIN_VALUE, ((Integer)value).floatValue(), TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
            }
            return coefficient.evaluate().between(Float.MIN_VALUE, value, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
        } else {
            throw new IllegalArgumentException();
        }
//        return coefficient.evaluate().between( Integer.MIN_VALUE, value, TreeResultOperator.LARGER, TreeResultOperator.SMALLER);
    }
}
