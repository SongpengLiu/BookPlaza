package com.example.bookplaza.file;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.FileUtils;

import com.example.bookplaza.data.User;
import com.example.bookplaza.user.UserOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description UserFileOperation
 */

public class UserFileOperation implements FileOperation {
    private static UserFileOperation instance = new UserFileOperation();

    private UserFileOperation() {
    }

    /**
     * @return com.example.bookplaza.file.UserFileOperation
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getInstance
     */
    protected static UserFileOperation getInstance() {
        return instance;
    }

    /**
     * @param object
     * @return void
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description save
     */
    @Override
    public void save(Object object) {
        User user;
        if (object instanceof User) {
            user = (User) object;
        } else {
            return;
        }

        if(UserOperation.APPLICATION_DIR == null){
            return;
        }

        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(user);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(UserOperation.APPLICATION_DIR,"user_" + user.getUsername() + ".json"));
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param username
     * @return java.lang.Object
     * @throws FileNotFoundException
     * @throws IOException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description load
     */
    @Override
    public Object load(String username) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(UserOperation.APPLICATION_DIR,"user_" + username + ".json"));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            char[] buffer = new char[inputStream.available()];
            inputStreamReader.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer);
            inputStreamReader.close();

            Gson gson = new GsonBuilder().create();
            User user = gson.fromJson(jsonString, new TypeToken<User>() {
            }.getType());
            return user;
        } catch (FileNotFoundException exception) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param username
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description delete
     */
    @Override
    public void delete(String username) {
        File userFile = new File(UserOperation.APPLICATION_DIR, "user_" + username + ".json");
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    /**
     * @author Songpeng Liu
     * @date 10/10/2023
     * @description copyImage
     * @param inputStream, username
     * @return boolean
     * @throws RuntimeException
     */
    public boolean copyImage(InputStream inputStream, String username){
        if(inputStream == null){
            return false;
        }
        String displayName = "user_image_"+username+".jpg";
        File target = new File(UserOperation.APPLICATION_DIR,displayName);
        if(target.exists()){
            target.delete();
        }
        try {
            Files.copy(inputStream,target.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * @author Songpeng Liu
     * @date 10/01/2023
     * @description convertData
     * @param inputStream
     * @return void
     * @throws RuntimeException
     */
    public void convertData(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while((line = bufferedReader.readLine()) != null){
                String[] array = line.split(",");
                System.out.println(array[0]);
                User user = (User)this.load(array[0].trim());
                if(user !=null){
                    continue;
                }
                user = new User(array[0].trim(), array[1].trim());
                this.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
