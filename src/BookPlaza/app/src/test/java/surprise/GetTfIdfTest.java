package surprise;

import android.content.Context;
import com.example.bookplaza.io.HashMapReader;
import com.example.bookplaza.surprise.TfIdf;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Test calculate and store TF-IDF value
 * NOTE: tfIdf.json must be in the asset path
 * If it's not there, run CalculateAndStoreTfIdfTest.java
 * @author Yucheng Zhu
 */
public class GetTfIdfTest {
    TfIdf tfIdf = new TfIdf();

    // small value to compare floats
    private final static double DELTA = 0.000000000001;

    public GetTfIdfTest() throws IOException {
    }

    @Test
    public void getTfIdfTest() throws IOException {
        // static variable also works. A genre that is not so special in this book
        assertEquals(
                0.0787326381067137,
                TfIdf.getTfIdf(tfIdf.getTfIdfMap(), 0, 240),
                DELTA
        );

        // A genre that is not so special in this book
        assertEquals(
                0.0787326381067137,
                tfIdf.getTfIdf(0, 240),
                DELTA
        );

        // a genre found only in this book
        assertEquals(
                0.14792192613988636,
                tfIdf.getTfIdf(0, 2319),
                DELTA
        );

        // a book not does not have this genre
        assertNull(
                tfIdf.getTfIdf(0, 2)
        );


    }
    @Test
    public void testException2(){
        try {
            new TfIdf().readTfIdf(null);
            TfIdf tfIdf1 = new TfIdf(null);
        } catch (IOException e) {

        }
    }
}
