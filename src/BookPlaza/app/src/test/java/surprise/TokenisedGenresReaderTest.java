package surprise;

import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.surprise.TokenisedGenresReader;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TokenisedGenresReaderTest {

    // object for this test
    TokenisedGenresReader tokenisedGenresReader = new TokenisedGenresReader();

    public TokenisedGenresReaderTest() throws IOException {
    }

    /**
     * Test read tokenised genre
     * @author Yucheng Zhu
     */
    @Test
    public void testReadTokenisedGenres() throws IOException {
        List<HashSet<Integer>> booksGenres = tokenisedGenresReader.readTokenisedGenres();

        // test the first book's genres
        HashSet<Integer> firstDoc = (HashSet<Integer>) IndicesReader.toIndices(
                "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53"
        );
        assertEquals(firstDoc, booksGenres.get(0));
    }

}
