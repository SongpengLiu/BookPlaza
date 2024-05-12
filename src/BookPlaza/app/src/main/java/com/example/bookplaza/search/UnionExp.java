package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;
import java.util.HashSet;

;

/**
 * LitExp: it is extended from the abstract class Exp,
 * 		   This class is used to represented the expression of 32-bit unsigned integer
 *
 * You are not required to implement any function inside this class.
 * Please do not change any thing inside this class as well.
 */

public class UnionExp extends Exp {
    Exp factor;
    Exp term;

    public UnionExp(Exp factor, Exp term) {
        this.factor = factor;
        this.term = term;

    }

    @Override
    public String show() {
        return "(" + factor.show() + " | " + term.show() + ")";
    }

    @Override
    public BST<Integer> evaluate() {
        return null;
    }

    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        ArrayList<TreeData<Integer>> union = new ArrayList<>();

        union.addAll(term.evaluateBooks(attributes));
        union.addAll(factor.evaluateBooks(attributes));
        union = removeDuplicates(union);
        return union;
    }

    public static <D extends Comparable<D>> ArrayList<TreeData<D>> removeDuplicates(ArrayList<TreeData<D>> list) {
        HashSet<Integer> set = new HashSet<>();
        ArrayList<TreeData<D>> uniqueList = new ArrayList<>();

        for (TreeData<D> item : list) {
            if (set.add(item.getId())) {
                uniqueList.add(item);
            }
        }
        return uniqueList;
    }
}
