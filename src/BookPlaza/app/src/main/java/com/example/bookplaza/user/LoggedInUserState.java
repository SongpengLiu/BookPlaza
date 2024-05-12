package com.example.bookplaza.user;
import android.content.Context;
import android.net.Uri;
import android.os.FileUtils;

import com.example.bookplaza.data.Books;
import com.example.bookplaza.data.User;

import com.example.bookplaza.file.BookFileOperation;
import com.example.bookplaza.file.FileOperationFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


import com.example.bookplaza.file.UserFileOperation;


public class LoggedInUserState implements UserState {
    User user;

    public LoggedInUserState(User user){
        this.user = user;
    }
    @Override
    public User login(String username, String password) {
        return null; //Cannot log in logged in state
    }


    @Override
    public Books searchBooks(List<Integer> list) {
        BookFileOperation bookFileOperation = (BookFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS);
        return bookFileOperation.load(list);


    }

    @Override
    public boolean changeImage(InputStream inputStream) {
        UserFileOperation userFileOperation = (UserFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER);
        boolean result = false;
        result = userFileOperation.copyImage(inputStream, user.getUsername());
        if(!result){
            return false;
        }
        user.setIsCustomImage(true);
        userFileOperation.save(user);
        return true;
    }


    @Override
    public User getUser(){
        return user;
    }
    @Override
    public boolean register(String username, String password){return false;}
    @Override
    public void saveSearchHistory(String history){
        user.addSearchHistory(history);
        FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER).save(user);
    }
    @Override
    public List<String> getSearchHistory(){
        return user.getSearchHistory();
    }

}
