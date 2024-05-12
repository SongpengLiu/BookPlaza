package com.example.bookplaza.search;

import com.example.bookplaza.trees.BST;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.TreeData;

import java.util.ArrayList;
import java.util.HashSet;


public class InterExp extends Exp {
    Exp factor;
    Exp term;

    public InterExp(Exp factor, Exp term) {
        this.factor = factor;
        this.term = term;

    }

    @Override
    public String show() {
        return "(" + factor.show() + " & " + term.show() + ")";
    }

    @Override
    public BST<Integer> evaluate() {
        return null;
    }

    //TODO
    @Override
    public ArrayList<TreeData<Integer>> evaluateBooks(BooksAttributes attributes) {
        ArrayList<TreeData<Integer>> intersection = new ArrayList<>();
        HashSet<Integer> numbers = new HashSet<>();

        for (TreeData<Integer> entry: factor.evaluateBooks(attributes)) {
            numbers.add(entry.getId());
        }
        for (TreeData <Integer> entry: term.evaluateBooks(attributes)) {
            if (numbers.contains(entry.getId())) {
                intersection.add(entry);
            }
        }
        
        System.out.println("evaluateBooks(): intersection="+intersection);
        return intersection;
    }
}
