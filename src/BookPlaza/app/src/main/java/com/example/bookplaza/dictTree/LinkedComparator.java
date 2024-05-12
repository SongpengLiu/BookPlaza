package com.example.bookplaza.dictTree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * LinkedComparator is a helper class to get key in O(1) and value in O(log n)
 * It sets a way to sort TreeSet's value by the HashMap's values.
 * This allows PreferenceValues combine a HashMap for the keys and a TreeSet for the values.
 * So that it finds a key in O(1) and a value in O(log n)
 * @author Yucheng Zhu
 */
public class LinkedComparator <Key extends Number, Value extends Number> implements Comparator<Key> {
    private final Map<Key, Value> base;

    /**
     * Reads a hash map with unique keys. Values can duplicate
     * @author Yucheng Zhu
     * @param map A hash map for keys and tree set for values
     */
    public LinkedComparator(HashMap<Key, Value> map) {
        this.base = map;
    }

    /**
     * First compare values. If equal, compare key
     * @author Yucheng Zhu
     * @param a First key
     * @param b Second key
     * @return 1 if a > b, -1 if a < b, else 0
     */
    public int compare(Key a, Key b) {
        // -- first compare values
        double diff = (Double)base.get(a).doubleValue() - base.get(b).doubleValue();

        // accept a tiny difference
        int compare;
        double EPSILON = 0.0000000001;
        if (Math.abs(diff) <= EPSILON) {
            compare = 0; // Consider them equal if the difference is within EPSILON
        } else {
            compare = diff < 0 ? -1 : 1; // Otherwise, compare as usual
        }

        // -- if values are equal, compare keys
        if (compare == 0) {
            return Double.compare(a.doubleValue(), b.doubleValue()); // keys are always unique
        } else {
            return compare;
        }
    }
}
