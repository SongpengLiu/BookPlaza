package surprise;

import com.example.bookplaza.dictTree.DictTree;
import com.example.bookplaza.dictTree.NumbersPair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test DictTree
 * @author Yucheng Zhu
 */
public class DictTreeTest {
    private final static float DELTA = 0.000000001F;

    /**
     * Test all operations in DictTree
     * @author Yucheng Zhu
     */
    @Test
    public void testDictTree() {
        HashMap<Integer, Float> map = new HashMap<>();
        map.put(1462, 6.8F);
        map.put(2793, 13.34F);
        // add the rest of your data here...

        // Links a HashMap to a TreeSet to find a key in O(1) and value in O(log n)
        DictTree<Integer, Float> dictTree = new DictTree<>(map);

        // add a key-value pair to dict tree in O(log n)
        dictTree.put(3000, 15.0F);

        // delete a value and its corresponding key from dict tree in O(log n)
        dictTree.remove(1462);

        // find the value by its key in dict tree in O(1)
        assertEquals(
                15.0F,
                dictTree.get(3000),
                DELTA
        );

        // find the smallest value and its corresponding key in dict tree in O(log n)
        assertEquals(
                new NumbersPair<>(2793, 13.34F),
                dictTree.smallest()
        );

        // find the largest value and its corresponding key in dict tree in O(log n)
        assertEquals(
                new NumbersPair<>(3000, 15.0F),
                dictTree.largest()
        );

        // test memorised keys
        ArrayList<Integer> memorisedKeys = new ArrayList<>();
        memorisedKeys.add(3000);
        dictTree.setMemorisedKeys(memorisedKeys);

        // test getGroup
        assertEquals(
                0,
                dictTree.getGroup(2793)
        );

        // test getGroup: same group as one of the memorised keys
        assertEquals(
                1,
                dictTree.getGroup(3000)
        );
    }

    /**
     * Test update value
     * @author Yucheng Zhu
     */
    @Test
    public void testUpdateValue() {
        // create a DictTree
        HashMap<Integer, Float> map = new HashMap<>();
        map.put(1, 6.8F);
        map.put(2, 4.2F);
        map.put(3, 13.34F);
        map.put(4, 2.5F);

        // check its original values
        DictTree<Integer, Float> dictTree = new DictTree<>(map);
        assertEquals(new TreeSet<>(Arrays.asList(new Integer[] {4, 2, 1, 3})), dictTree.getValuesTree());

        // change a value
        dictTree.updateValue(3, 1.1F);
        // the order is correct
        assertEquals(new TreeSet<>(Arrays.asList(new Integer[] {3, 4, 2, 1})), dictTree.getValuesTree());
        assertEquals("{1=6.8, 2=4.2, 3=1.1, 4=2.5}", dictTree.getDict().toString());
    }

}
