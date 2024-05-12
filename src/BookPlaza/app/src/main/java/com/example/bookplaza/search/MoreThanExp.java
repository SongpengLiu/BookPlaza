package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;
import com.example.bookplaza.trees.TreeResultOperator;

import java.util.ArrayList;
import java.util.Map;


public class MoreThanExp<D extends Comparable<D>> extends Exp {

    Exp coefficient;
    D value;

    public MoreThanExp(Exp coefficient, D value) {
        this.coefficient = coefficient;
        this.value = value;

    }

    @Override
    public String show() {
        return coefficient.show() + ">" + value;
    }

    @Override
    public BST evaluate() {
        return null;
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {

        // Return an empty ArrayList if the tree is empty
        System.out.println("MoreThanExp.evaluateBooks(): value="+value);
        System.out.println("MoreThanExp.evaluateBooks(): value.getClass()="+value.getClass());

        if (coefficient.evaluate().getRoot().isEmpty(coefficient.evaluate().getRoot())) {
            return new ArrayList<>();
        }

        // the tree is not empty
        if (coefficient.evaluate().getRoot().getContent().getComparable() instanceof Integer) {
            if (value.getClass() == Float.class) {
                return coefficient.evaluate().between(((Float)value).intValue(), Integer.MAX_VALUE, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
            }
            return coefficient.evaluate().between(value,Integer.MAX_VALUE, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
        } else if (coefficient.evaluate().getRoot().getContent().getComparable() instanceof Float) {
            if (value.getClass() == Integer.class) {
//                return coefficient.evaluate().between((Float) value,Float.MAX_VALUE, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
                return coefficient.evaluate().between(((Integer)value).floatValue(), Float.MAX_VALUE, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
            }
            return coefficient.evaluate().between(value,Float.MAX_VALUE, TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER);
        } else {
            throw new IllegalArgumentException();
        }
//        return coefficient.evaluate().between(value,Integer.MAX_VALUE, TreeResultOperator.LARGER, TreeResultOperator.SMALLER);
    }
}
