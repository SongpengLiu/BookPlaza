package com.example.bookplaza.trees;

import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.Reader;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Test finding a book FAST
 * @author Yucheng Zhu
 */
public class LibraryTest {
    Library library;
    @Before
    public void init() throws IOException {
        library = new Library(Reader.assetsFolder(Data.BOOK_CSV_FILE));
    }

    @Test
    public void testGet() {
        assertEquals(
                "0,Beautiful Disaster,Jamie McGuire,4.02,666487,33.05,Romance,Amazon,https://www.amazon.com/s?k=Jamie+McGuire+Beautiful+Disaster",
                library.get(0).toString()
        );
        assertEquals(
                "1,Fifty Shades of Grey,E.L. James,3.66,2491856,21.05,Romance,Barnes & Nobles,https://www.barnesandnoble.com/s/E.L.+James+Fifty+Shades+of+Grey",
                library.get(1).toString()
        );
    }
}
