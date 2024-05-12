package com.example.bookplaza.io;

import android.content.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Writer {
    /**
     *
     * @author Yucheng Zhu
     * @param outputStream File output stream
     * @param data The string to write
     */
    public static void write(OutputStream outputStream, String data) throws IOException {
        try {
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Cannot write to file.");
        }
    }

    /**
     * Write a file's content in Android device as a string
     * Folder: data/data/com.example.bookplaze/files
     * @author Yucheng Zhu
     * @param context An Android Object
     * @param filePath File path to write the file
     */
    public static OutputStream dataFolder(Context context, String filePath) throws IOException {
            return context.openFileOutput(filePath, Context.MODE_PRIVATE);
    }

    /**
     * Write a file's content in a non-Android file
     * Folder: data/data/com.example.bookplaze/files
     * @author Yucheng Zhu
     * @param filePath File path to write the file
     */
    public static OutputStream dataFolder(String filePath) throws IOException {
            return Files.newOutputStream(Paths.get("src/main/assets/" + filePath));
    }
}

