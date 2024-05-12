package indices;

import com.example.bookplaza.dictTree.NumbersPair;
import com.example.bookplaza.io.Data;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


/**
 * Test data static variables
 * @author Yucheng Zhu
 */
public class DataTest {
    // Singleton
    Data data;

    public DataTest() throws IOException {
        data = Data.getInstance();
    }

    /**
     * Test data static variables
     * @author Yucheng Zhu
     */
    @Test
    public void dataTest() throws IOException {

        assertEquals("test_books_list_3000_realistic.csv", Data.BOOK_CSV_FILE);

        // number of books
        assertEquals(2900, data.BOOKS_COUNT);

        // test tripartite book groups
        assertEquals(Arrays.toString(new int[] {966, 966, 968}), Arrays.toString(data.tripartiteBooksGroups()));

        // test beginning and end indices in a given book group
        assertEquals(new NumbersPair<>(0, 965), data.calculateTripartiteIndices(0));
        assertEquals(new NumbersPair<>(966, 1931), data.calculateTripartiteIndices(1));
        assertEquals(new NumbersPair<>(1932, 2899), data.calculateTripartiteIndices(2));
    }

    /**
     * Test find which group a book is in
     * @author Yucheng Zhu
     */
    @Test
    public void findGroupTest() throws IOException {
        // find book in the first group
        assertEquals(0, data.findGroup(0));
        assertEquals(0, data.findGroup(5));
        assertEquals(0, data.findGroup(965));

        // find book in the second group
        assertEquals(1, data.findGroup(966));
        assertEquals(1, data.findGroup(1024));
        assertEquals(1, data.findGroup(1931));

        // find book in the third group
        assertEquals(2, data.findGroup(1932));
        assertEquals(2, data.findGroup(2011));
        assertEquals(2, data.findGroup(2899));

        // illegal book indices
        assertThrows(IllegalArgumentException.class, () -> data.findGroup(-1));
        assertThrows(IllegalArgumentException.class, () -> data.findGroup(2900));
    }
}
