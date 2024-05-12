package io;

import com.example.bookplaza.io.HashMapReader;
import com.example.bookplaza.io.HashMapWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test HashMap IO
 * @author Yucheng Zhu
 */
public class HashMapIOTest {
    private HashMapReader<HashMap<Integer, HashMap<Integer, Double>>> hashMapReaderTI = new HashMapReader<>();
    private HashMapReader<HashMap<Integer, Double>> hashMapReaderPreference = new HashMapReader<>();

    private HashMapWriter hashMapWriter = new HashMapWriter();
    private HashMap<Integer, HashMap<Integer, Double>> mapTI;
    private HashMap<Integer, Double> mapPreference;
    @Before
    public void init() {
        // test readTfIdfMap
        mapTI = new HashMap<>();

        HashMap<Integer, Double> set1 = new HashMap<>();
        set1.put(1, 11.1);
        set1.put(2, 12.2);
        mapTI.put(1, set1);

        HashMap<Integer, Double> set2 = new HashMap<>();
        set2.put(11, 21.1);
        set2.put(12, 22.2);
        mapTI.put(2, set2);

        // test readPreferenceValues
        mapPreference = new HashMap<>();
        mapPreference.put(1, 11.1);
        mapPreference.put(2, 12.2);
    }

    /**
     * Test file writing and reading gives the correct value.
     * @author Yucheng Zhu
     * @throws IOException File not found or stream closed
     */
    @Test
    public void testIO() throws IOException {
        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));

        // -- test writing and reading TF-IDF map
        // test writing
        OutputStream outputStream = Files.newOutputStream(Paths.get("src/main/assets/readerTest1"));
        hashMapWriter.write(outputStream, mapTI);

        // read the written values
        InputStream inputStream = Files.newInputStream(Paths.get("src/main/assets/readerTest1"));
        assertEquals(mapTI, hashMapReaderTI.readTfIdfMap(inputStream));

        // -- test writing and reading preference values
        // test writing
        OutputStream outputStream2 = Files.newOutputStream(Paths.get("src/main/assets/readerTest1"));
        hashMapWriter.write(outputStream2, mapPreference);

        // read the written values
        InputStream inputStream2 = Files.newInputStream(Paths.get("src/main/assets/readerTest1"));
        assertEquals(mapPreference, hashMapReaderPreference.readPreferenceValues(inputStream2));

        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));
    }

    /**
     * Test HashMap writing and reading gives the correct value.
     * @author Yucheng Zhu
     * @throws IOException File not found or stream closed
     */
    @Test
    public void testIOFileException() throws IOException {
        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));

        // test writing
        OutputStream outputStream = Files.newOutputStream(Paths.get("src/main/assets/readerTest1"));
        outputStream.close();
        assertThrows(IOException.class, () -> hashMapWriter.write(outputStream, mapTI));

        // read the written values
        InputStream inputStream = Files.newInputStream(Paths.get("src/main/assets/readerTest1"));
        inputStream.close();
        assertThrows(IOException.class, () -> hashMapReaderTI.readTfIdfMap(inputStream));

        Files.deleteIfExists(Paths.get("src/main/assets/readerTest1"));
    }

    /**
     * Test InfoForBooksChangedGroups IO
     * @author Yucheng Zhu
     * @throws IOException File not found or stream closed
     */
    @Test
    public void testInfoForBooksChangedGroups() throws IOException {
        // delete existing file
        Files.deleteIfExists(Paths.get("src/main/assets/readerTestGroupsChanges2"));

        // -- Stub expected value
        HashSet<ArrayList<Integer>> oldAndNewBooksGroup = new HashSet<>();
        // first book that changed group
        ArrayList<Integer> newChange1 = new ArrayList<>(Arrays.asList(new Integer[] {18, 1, 2}));
        ArrayList<Integer> newChange2 = new ArrayList<>(Arrays.asList(new Integer[] {2548, 2, 1}));
        oldAndNewBooksGroup.add(newChange1);
        oldAndNewBooksGroup.add(newChange2);

        // second book that changed group
        ArrayList<Integer> newChange3 = new ArrayList<>(Arrays.asList(new Integer[] {1108, 1, 2}));
        ArrayList<Integer> newChange4 = new ArrayList<>(Arrays.asList(new Integer[] {2774, 2, 1}));
        oldAndNewBooksGroup.add(newChange3);
        oldAndNewBooksGroup.add(newChange4);

        // test write it to file
        HashMapWriter hashMapWriter = new HashMapWriter();
        OutputStream outputStream = Files.newOutputStream(Paths.get("src/main/assets/readerTestGroupsChanges2"));
        hashMapWriter.write(outputStream, oldAndNewBooksGroup);

        // test read it from the file
        HashMapReader<HashSet<ArrayList<Integer>>> hashMapReader = new HashMapReader<>();
        InputStream inputStream = Files.newInputStream(Paths.get("src/main/assets/readerTestGroupsChanges2"));
        assertEquals(oldAndNewBooksGroup, hashMapReader.readInfoForBooksChangedGroups(inputStream));

        Files.deleteIfExists(Paths.get("src/main/assets/readerTestGroupsChanges2"));
    }
}
