package com.example.bookplaza.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description Books class
 * @throws RuntimeException
 */
public class Books {
    List<Book> books = new ArrayList<>();

    public Books() {
    }

    public Books(Books books) {
        this.books = books.getBooksList();
    }

    /**
     * @param book
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description addBook
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * @param index
     * @return com.example.bookplaza.data.Book
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getBook
     * @deprecated only for test
     */
    public Book getBook(Integer index) {
        for (Book book : books) {
            if (book.getIndex().equals(index)) {
                return book;
            }
        }
        return null;
    }


    /**
     * @return java.util.ArrayList<com.example.bookplaza.data.Book>
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getBooksList
     */
    public List<Book> getBooksList() {
        return books;
    }

    /**
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description clearBooks
     */
    public void clearBooks() {
        books.clear();
    }

    /**
     * @return boolean
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description isEmpty
     */
    public boolean isEmpty() {
        return books.isEmpty();
    }

    public void sortDefault(){
        //todo
    }
    public void sortByName(){
        TreeMap<String, Book> map = new TreeMap<>();
        for(Book book: books){
            map.put(book.getName(), book);
        }
        List<Book> list =map.values().stream().collect(Collectors.toList());
        books = list;
    }
    public void sortByPriceLowToHigh(){
        TreeMap<Float, Book> map = new TreeMap<>();
        for(Book book: books){
            map.put(book.getPrice(), book);
        }
        List<Book> list =map.values().stream().collect(Collectors.toList());
        books = list;
    }
    public void sortByPriceHighToLow(){
        TreeMap<Float, Book> map = new TreeMap<>();
        for(Book book: books){
            map.put(book.getPrice(), book);
        }
        List<Book> list =map.values().stream().collect(Collectors.toList());
        Collections.reverse(list);
        books = list;
    }
    public void sortByRating(){
        TreeMap<Float, Book> map = new TreeMap<>();
        for(Book book: books){
            map.put(book.getReviewScore(), book);
        }
        List<Book> list =map.values().stream().collect(Collectors.toList());
        books = list;
    }
}
