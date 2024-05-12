package com.example.bookplaza.io;

import android.content.Context;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Write hash map
 * @author Yucheng Zhu
 */
public class HashMapWriter {

    /**
     * Write hash map
     * @author Yucheng Zhu
     */
    public <T> void write (
            OutputStream outputStream,
            T map
    ) throws IOException {
        Gson gson = new Gson();
        String data = gson.toJson(map);

        Writer.write(outputStream, data);
    }
}
