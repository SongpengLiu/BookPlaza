package indices;

import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HashMapWriterTest {
    @Test
    public void testSaveToFile() {
        HashMap<Integer, TreeMap<String, Double>> map = new HashMap<>();

        TreeMap<String, Double> tree1 = new TreeMap<>();
        tree1.put("1a", 11.1);
        tree1.put("1b", 12.2);
        map.put(1, tree1);

        TreeMap<String, Double> tree2 = new TreeMap<>();
        tree2.put("2a", 21.1);
        tree2.put("2b", 22.2);
        map.put(2, tree2);

        System.out.println(map);

//        assertTrue(true);
    }

}
