package com.example.bookplaza.user;

import android.net.Uri;
import com.example.bookplaza.data.Books;
import com.example.bookplaza.file.BookFileOperation;
import com.example.bookplaza.file.FileOperation;
import com.example.bookplaza.file.FileOperationFactory;
import com.example.bookplaza.file.UserFileOperation;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description UserOperation class
 */
public class UserOperation {
    public static File APPLICATION_DIR = null;
    static UserSession userSession = null;

    static UserOperation instance = new UserOperation();
    public static Integer MAX_INDEX = 0;

    private UserOperation() {
    }

    public static UserOperation getInstance() {
        if(APPLICATION_DIR == null){
            throw new RuntimeException("Should set Dir at First");
        }


        if(userSession == null) {
            userSession = new UserSession();
        }
        return instance;
    }
    public static void setApplicationDir(File inputDir){
        APPLICATION_DIR = inputDir;
    }
    public Uri getUerImageUri(){
        return userSession.getUerImageUri();
    }

    /**
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description dataCheck
     */
    public void dataCheck(InputStream userDataSource, InputStream bookDataSource) {
        //Check user data
        FileOperation fileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER);
        ((UserFileOperation) fileOperation).convertData(userDataSource);

        //check book
        fileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS);
        ((BookFileOperation) fileOperation).convertData(bookDataSource);
    }

    public boolean login(String username, String password) {
        return userSession.login(username,password);
    }

    public boolean logout() {
        return userSession.logout();
    }

    public Books searchBooks(List<Integer> list) {
        return userSession.searchBooks(list);
    }

    public boolean changeImage(InputStream inputStream) {
        return userSession.changeImage(inputStream);
    }
    public String getUsername(){
        return userSession.getUsername();
    }

    public boolean register(String username, String password){
        return userSession.register(username, password);
    }

    public void saveSearchHistory(String history){
        userSession.saveSearchHistory(history);
    }

    public List<String> getSearchHistory(){
        return userSession.getSearchHistory();
    }

}
