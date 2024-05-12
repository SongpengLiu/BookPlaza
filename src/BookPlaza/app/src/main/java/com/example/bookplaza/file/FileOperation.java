package com.example.bookplaza.file;

import android.content.Context;

import com.example.bookplaza.data.Book;
import com.example.bookplaza.data.User;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description FileOperation interface
 */
public interface FileOperation {
    /**
     * @param object
     * @return void
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description save
     */
    public void save(Object object);

    /**
     * @param key
     * @return java.lang.Object
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description load
     */
    public Object load(String key);

    /**
     * @param key
     * @return void
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description delete
     */
    public void delete(String key);

}
