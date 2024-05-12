package indices;

import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.io.Reader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test IndicesReader
 *
 * @author Yucheng Zhu
 */
public class IndicesReaderTest {
    InputStream inputStream; // define the test file's location
    String fileContent; // file's content as a string

    /**
     * Load all attributes into the optimal data structure so that they can be sorted and searched in O(1) or o(log n).
     * @author Yucheng Zhu
     */
    @Before
    public void init() throws IOException {
        // define the test file's location
        try {
            inputStream = Files.newInputStream(
                            Paths.get("src/main/assets/test_group").toAbsolutePath()
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read file's content as a string
        fileContent = Reader.read(inputStream);
    }

    /**
     * Test read file content as a string
     * @author Yucheng Zhu
     */
    @Test
    public void testReadToString() {
        assertEquals("0,1,2,3,4", fileContent);
    }

    /**
     * Test file stream is closed exception
     * @author Yucheng Zhu
     */
    @Test
    public void testFileStreamIsClosedException() {

        // Create the path
        Path path = Paths.get("src/main/assets/test_group").toAbsolutePath();
        try {
            // create an input stream
            InputStream inputStream = Files.newInputStream(
                    Paths.get("src/main/assets/test_group").toAbsolutePath()
            );

            // close the file
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read file's content as a string
        assertThrows(IOException.class, () -> Reader.read(inputStream));
    }

    /**
     * Test convert file content to indices
     * @author Yucheng Zhu
     */
    @Test
    public void testToIndices() {
        // test string to indices
        HashSet<Integer> indices1 = new HashSet<>(6);
        indices1.add(1);
        indices1.add(2);
        indices1.add(3);
        assertEquals(indices1, IndicesReader.toIndices("1,2,3"));

        // test string to indices
        HashSet<Integer> indices2 = new HashSet<>();
        assertEquals(indices2, IndicesReader.toIndices(""));
    }

    /**
     * Test counts file's lines correctly
     * @author Yucheng Zhu
     */
    @Test
    public void testCountLines() {
        // count files' lines
        assertEquals(3, Data.countLines("1\n2\n3"));

        // count files' lines with an empty line
        assertEquals(4, Data.countLines("1\n2\n3\n"));

        // count empty file has 1 line
        assertEquals(1, Data.countLines(""));

        // count file with nothing but empty lines
        assertEquals(3, Data.countLines("\n\n"));
    }
}
