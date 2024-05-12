package zzSlowTests;

import com.example.bookplaza.surprise.TfIdfGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Test calculate and store TF-IDF value
 * NOTE: THIS TEST CAN TAKE 2 MINUTES
 * @author Yucheng Zhu
 */
public class CalculateAndStoreTfIdfTest {
    private TfIdfGenerator tfIdfGenerator;
    /**
     * Create TfIdfGenerator object
     * @author Yucheng Zhu
     */
    @Before
    public void init() {
        // create a new TF-IDF matrix
        tfIdfGenerator = new TfIdfGenerator();
    }

    /**
     * Test storing TF-IDF
     * NOTE: THIS TEST CAN TAKE 2 MINUTES
     * @author Yucheng Zhu
     */
    @Test
    public void testCalculateAndStoreTfIdfFromGenres() throws IOException {
        System.out.println("CalculateAndStoreTfIdfTest: TF-IDF calculation for 2900 books can take about 2 minutes");
        tfIdfGenerator.calculateAndStoreTfIdfFromGenres();
    }

}
