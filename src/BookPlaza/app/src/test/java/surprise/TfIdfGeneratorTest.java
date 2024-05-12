package surprise;

import com.example.bookplaza.surprise.TfIdf;
import com.example.bookplaza.surprise.TfIdfGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test calculated TF-IDF value
 * @author Yucheng Zhu
 */
public class TfIdfGeneratorTest {
    private TfIdfGenerator tfIdfGenerator;
    private List<HashSet<Integer>> docs;

    // A tiny value to compare doubles
    private final static double DELTA = 0.00000001;

    /**
     * Create test documents
     * @author Yucheng Zhu
     */
    @Before
    public void init() {
        // create a test document
        docs = new ArrayList<>();
        docs.add(toHashMap("1,2,3,4,5"));
        docs.add(toHashMap("3,7,1,8,2"));
        docs.add(toHashMap("3,5,1,8,1"));

        // create a new TF-IDF matrix
        tfIdfGenerator = new TfIdfGenerator();
    }

    /**
     * A helper function to help create a test document from a string
     * @author Yucheng Zhu
     */
    private HashSet<Integer> toHashMap(String indices) {
        HashSet<Integer> doc = new HashSet<>();
        String[] indicesArray = indices.split(",");
        for (String index : indicesArray) {
            doc.add(Integer.parseInt(index));
        }
        return doc;
    }

    /**
     * Test term frequency
     * @author Yucheng Zhu
     */
    @Test
    public void testTf() {
        // That's the only thing I read
        assertEquals(1.0, tfIdfGenerator.tf(toHashMap("1"), 1), DELTA);

        // I read it sometimes
        assertEquals(0.2, tfIdfGenerator.tf(docs.get(1), 2), DELTA);

        // I've never read that!
        assertEquals(0.0, tfIdfGenerator.tf(docs.get(1), 5), DELTA);
    }

    /**
     * Test inverse document frequency
     * @author Yucheng Zhu
     */
    @Test
    public void testIDf() {

        // everyone's read it
        assertEquals(0.0, tfIdfGenerator.idf(docs, 1), DELTA);

        // someone's read it
        assertEquals(0.4054651081081644, tfIdfGenerator.idf(docs, 2), DELTA);
        assertEquals(0.4054651081081644, tfIdfGenerator.idf(docs, 5), DELTA);

        // nobody reads it
        assertEquals(Double.POSITIVE_INFINITY, tfIdfGenerator.idf(docs, 9), DELTA);
    }

    /**
     * Test calculate and store TF-IDF
     * @author Yucheng Zhu
     */
    @Test
    public void testCalculateAndStoreTfIdf() {

        tfIdfGenerator.calculateAndStoreTfIdf(docs);


        // everyone likes it
        assertEquals(0.0, TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 1, 1), DELTA);

        // some people like it
        assertEquals(0.08109302162163289, TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 2, 1), DELTA);
        assertEquals(0.08109302162163289, TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 8, 1), DELTA);

        // an item that interests only me
        assertEquals(0.21972245773362198, TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 7, 1), DELTA);

        // I've never read that
        assertNull(TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 5, 1));

        // nobody's ever read that
        assertNull(TfIdf.getTfIdf(tfIdfGenerator.getTfIdfMap(), 9, 1));
    }
}
