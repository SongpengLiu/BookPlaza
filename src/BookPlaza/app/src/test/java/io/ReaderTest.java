package io;

import com.example.bookplaza.io.Reader;
import com.example.bookplaza.io.Writer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test file IO
 * @author Yucheng Zhu
 */
public class ReaderTest {

    /**
     * Test file writing and read gives the correct value.
     * @author Yucheng Zhu
     * @throws IOException File not found or stream closed
     */
    @Test
    public void testIO() throws IOException {
        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));

        // test writing
//        OutputStream outputStream = Files.newOutputStream(Paths.get("src/main/assets/readerTest1"));
        OutputStream outputStream = Writer.dataFolder("readerTest1");
        Writer.write(outputStream, "123");

        // read the written values
        InputStream inputStream = Reader.dataFolder("readerTest1");
        assertEquals("123", Reader.read(inputStream));

        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));
    }
    /**
     * Test file writing and read gives the correct value.
     * @author Yucheng Zhu
     * @throws IOException File not found or stream closed
     */
    @Test
    public void testIOFileException() throws IOException {
        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));

        // test writing
        OutputStream outputStream = Writer.dataFolder("readerTest1");
        outputStream.close();
        assertThrows(IOException.class, () -> Writer.write(outputStream, "123"));

        // read the written values
        InputStream inputStream = Reader.dataFolder("readerTest1");
        inputStream.close();
        assertThrows(IOException.class, () -> Reader.read(inputStream));

        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));
    }
}
