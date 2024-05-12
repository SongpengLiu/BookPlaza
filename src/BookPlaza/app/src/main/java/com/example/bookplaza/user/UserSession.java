package com.example.bookplaza.user;

import android.content.Context;
import android.net.Uri;

import com.example.bookplaza.data.Books;
import com.example.bookplaza.data.User;


import java.io.InputStream;
import java.util.List;

import java.io.File;


public class UserSession {
    UserState userState = new LoggedOutUserState();
    public boolean login(String username, String password) {
        User user = userState.login(username, password);
        if( user!= null){
            this.userState = new LoggedInUserState(user);
            return true;
        }
        return false;
    }

    public boolean logout() {
        if(this.isLogin()){
            this.userState = new LoggedOutUserState();
            return true;
        }
        return false;
    }

    public Books searchBooks(List<Integer> list) {
        return userState.searchBooks(list);
    }

    public boolean changeImage(InputStream inputStream) {
        return userState.changeImage(inputStream);
    }

    public boolean isLogin(){
        return userState.getUser() != null;
    }

    public Uri getUerImageUri(){
        User user = userState.getUser();
        if(user==null || !user.getIsCustomImage()){
            return null;
        }
        return Uri.fromFile(new File(UserOperation.APPLICATION_DIR, "user_image_"+user.getUsername()+".jpg"));
    }
    public String getUsername(){
        if(this.isLogin()){
            return userState.getUser().getUsername();
        }
        else{
            return null;
        }
    }

    public boolean register(String username, String password){
        return userState.register(username, password);
    }

    public void saveSearchHistory(String history){
        userState.saveSearchHistory(history);
    }
    public List<String> getSearchHistory(){
        return  userState.getSearchHistory();
    }
}
