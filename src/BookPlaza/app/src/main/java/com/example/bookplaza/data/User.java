package com.example.bookplaza.data;

import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConstants;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description User class
 */
public class User {
    private String username;
    private String encryptedPassword;
    private boolean isCustomImage;

    private List<String> searchHistory = new ArrayList<>();

    /**
     * @param username
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description User
     */
    public User(String username, String password) {
        this.username = username;
        this.encryptedPassword = getEncryptedPassword(password);
        this.isCustomImage = false;
    }

    /**
     * @param password
     * @return void
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description setEncryptedPassword
     */
    private String getEncryptedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            md.update(password.getBytes(), 0, password.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param password
     * @return boolean
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description checkPassword
     */
    public boolean checkPassword(String password) {
        if(encryptedPassword.equals(getEncryptedPassword(password))){
            return true;
        }
        return false;
    }

    /**
     * @return java.lang.String
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getUsername
     */
    public String getUsername() {
        return this.username;
    }
    public void setIsCustomImage(boolean isCustomImage){
        this.isCustomImage = isCustomImage;
    }
    public boolean getIsCustomImage(){
        return this.isCustomImage;
    }
    public List<String> getSearchHistory(){
        return searchHistory;
    }
    public void addSearchHistory(String history){
        if(searchHistory.size()>=3){
            searchHistory.remove(searchHistory.size()-1);
        }
        searchHistory.add(0,history);
    }
}
