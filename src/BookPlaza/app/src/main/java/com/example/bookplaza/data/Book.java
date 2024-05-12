package com.example.bookplaza.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description Book class
 */
public class Book {
    public static final int MAX_BOOK_STRING_LENGTH = 512;

    Integer index;
    String name;
    String author;
    Float reviewScore;
    Integer reviewPeopleNumber;
    Float price;
    String genre;
    String seller;
    String url;

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description Book
     * @param index, name, author, reviewScore, reviewPeopleNumber, url
     * @throws RuntimeException
     */
    public Book(
            int index, String name, String author,
            Float reviewScore, Integer reviewPeopleNumber, Float price,
            String genre, String seller, String url
    ) {
        this.index = index;
        this.name = name;
        this.author = author;
        this.reviewScore = reviewScore;
        this.reviewPeopleNumber = reviewPeopleNumber;
        this.price = price;
        this.genre = genre;
        this.seller = seller;
        this.url = url;
        if (this.toString().length()>MAX_BOOK_STRING_LENGTH) {
            throw new RuntimeException("Book information too long");
        }
    }

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description Book
     * @param bookString
     * @throws RuntimeException
     */
    public Book(String bookString) {
        bookString = bookString.trim();
        if(bookString.length()>MAX_BOOK_STRING_LENGTH){
            throw new RuntimeException("Book information too long");
        }

        String[] bookArray = bookString.split(",");
        if(filterNull(bookArray[0])!= null){
            this.index = Integer.valueOf(filterNull(bookArray[0]));
        }
        this.name = filterNull(bookArray[1]);
        this.author= filterNull(bookArray[2]);
        if(filterNull(bookArray[3])!= null){
            this.reviewScore = Float.parseFloat(filterNull(bookArray[3]));
        }
        if(filterNull(bookArray[4])!= null){
            this.reviewPeopleNumber = Integer.valueOf(filterNull(bookArray[4]));
        }
        if(filterNull(bookArray[5])!= null){
            this.price = Float.parseFloat(filterNull(bookArray[5]));
        }
        this.genre = filterNull(bookArray[6]);
        this.seller = filterNull(bookArray[7]);
        this.url = filterNull(bookArray[8]);
    }

    private String filterNull(String s){
        s = s.trim();
        if(s.equals("null") || s.equals("")){
            return null;
        }
        return s.trim();
    }

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getIndex
     * @return java.lang.Integer
     */
    public Integer getIndex(){
        return index;
    }

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getName
     * @return java.lang.String
     */
    public String getName() {return name;}
    public String getAuthor() {return author;}
    public Float getPrice() {return price;}

    public Float getReviewScore() {
        return reviewScore;
    }

    public Integer getReviewPeopleNumber() {
        return reviewPeopleNumber;
    }

    public String getGenre() {
        return genre;
    }

    public String getSeller() {
        return seller;
    }

    public String getUrl() {
        return url;
    }

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description toString
     * @return java.lang.String
     */
    @Override
    public String toString(){
        return index+","+name+","+author+","+reviewScore+","+ reviewPeopleNumber+"," + price+"," + genre+"," + seller+"," + url;
    }

    /**
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description setName
     * @param name
     * @return void
     */
    public void setName(String name){
        this.name = name;
    }

}
