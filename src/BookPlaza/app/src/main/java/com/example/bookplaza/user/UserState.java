package com.example.bookplaza.user;

import android.content.Context;

import com.example.bookplaza.data.Books;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import android.net.Uri;

import com.example.bookplaza.data.User;


public interface UserState {

    public User login(String username, String password);

    public Books searchBooks(List<Integer> list);

    public boolean changeImage(InputStream inputStream);

    public User getUser();
    public boolean register(String username, String password);
    public void saveSearchHistory(String history);
    public List<String> getSearchHistory();

}
