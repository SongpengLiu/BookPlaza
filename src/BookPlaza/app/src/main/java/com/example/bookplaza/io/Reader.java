package com.example.bookplaza.io;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;

/**
 * Test file IO
 * @author Yucheng Zhu
 */
public class Reader<T> {
    /**
     * Read a file's content as a string
     *
     * @author Yucheng Zhu
     * @param inputStream The file's inputStream
     * @return The file's content as a string
     * @throws IOException Throws IOException when file does not exist
     */
    public static String read(InputStream inputStream) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Read the file into a string
            StringJoiner stringJoiner = new StringJoiner("\n");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringJoiner.add(line);
            }

            // Close everything to prevent errors
            bufferedReader.close();
            inputStream.close();

            // return the string
            return stringJoiner.toString();
        } catch (IOException e) {
            throw new IOException("Cannot find the file");
        }
    }

    /**
     * Generate a full path from the assets folder in the Android context
     * e.g. if the file name is "group0", it gives "assets/group0"
     *
     * @author Yucheng Zhu
     * @param context The context, an Android object
     * @param fileName The file's name (e.g. "group0")
     * @return The file's content as a string
     * @throws IOException Throws IOException when file does not exist
     */
    public static InputStream assetsFolder(Context context, String fileName) throws IOException {
        if (context == null) return Files.newInputStream(Paths.get("src/main/assets/" + fileName));
        else return context.getApplicationContext().getAssets().open(fileName);
    }

    /**
     * Generate a full path from the assets folder in a non-Android file
     * e.g. if the file name is "group0", it gives "assets/group0"
     *
     * @author Yucheng Zhu
     * @param fileName The file's name (e.g. "group0")
     * @return The file's content as a string
     * @throws IOException Throws IOException when file does not exist
     */
    public static InputStream assetsFolder(String fileName) throws IOException {
        return assetsFolder(null, fileName);
    }

    /**
     * Generate a full path from the data folder in the Android device
     * Folder: data/data/com.example.bookplaze/files
     *
     * @author Yucheng Zhu
     * @param context The context, an Android object
     * @param fileName The file's name (e.g. "group0")
     * @return The file's content as a string
     * @throws IOException Throws IOException when file does not exist
     */
    public static InputStream dataFolder(Context context, String fileName) throws IOException {
        return context.openFileInput(fileName);
    }

    /**
     * Generate a full path from the data folder in in a non-Android file
     * Folder: data/data/com.example.bookplaze/files
     *
     * @author Yucheng Zhu
     * @param fileName The file's name (e.g. "group0")
     * @return The file's content as a string
     * @throws IOException Throws IOException when file does not exist
     */
    public static InputStream dataFolder(String fileName) throws IOException {
        return Files.newInputStream(Paths.get("src/main/assets/" + fileName));
    }
}
