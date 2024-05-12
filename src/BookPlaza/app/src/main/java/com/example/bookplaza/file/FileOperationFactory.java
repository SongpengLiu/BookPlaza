package com.example.bookplaza.file;

import android.content.Context;

import java.io.File;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description FileOperationFactory
 */
public class FileOperationFactory{
    public static enum FileOperationType {USER, BOOKS}

    /**
     * @param type, context
     * @return com.example.bookplaza.file.FileOperation
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description createFileOperate
     */
    public static FileOperation createFileOperate(FileOperationType type) {
        if (type.equals(FileOperationType.USER)) {
            return UserFileOperation.getInstance();
        } else if (type.equals(FileOperationType.BOOKS)) {
            return BookFileOperation.getInstance();
        }
        return null;
    }
}
