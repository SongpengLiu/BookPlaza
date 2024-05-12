package com.example.bookplaza.user;

import android.content.Context;
import android.net.Uri;

import com.example.bookplaza.data.Books;
import com.example.bookplaza.data.User;
import com.example.bookplaza.file.FileOperation;
import com.example.bookplaza.file.FileOperationFactory;
import com.example.bookplaza.file.UserFileOperation;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LoggedOutUserState implements UserState {

    @Override
    public User login(String username, String password) {
        FileOperation userFileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER);
        User user = (User)userFileOperation.load(username);
        if(user!=null && user.checkPassword(password)){
            return user;
        }
        return null;
    }

    @Override
    public Books searchBooks(List<Integer> list) {
        return null; // Cannot search books in logged out state
    }

    @Override
    public boolean changeImage(InputStream inputStream) {
        return false; // Cannot change an image in logged out state
    }

    @Override
    public User getUser(){
        return null;
    }
    @Override
    public boolean register(String username, String password){
        User user = new User(username, password);
        FileOperation fileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER);
        if(fileOperation.load(username) == null){
            fileOperation.save(user);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void saveSearchHistory(String history){
        return;
    }
    @Override
    public List<String> getSearchHistory(){return null;}
}
