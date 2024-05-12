package com.example.bookplaza.dictTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * PreferenceValues combines HashMap and TreeSet to get key in O(1) and value in O(log n)
 * It uses LinkedComparator to compare TreeSet by HashMap's values.
 * So that it finds a key in O(1) and a value in O(log n)
 * @author Yucheng Zhu
 */
public class DictTree<Key extends Number, Value extends Number> {
    // HashMap to get key in O(1)
    private final HashMap<Key, Value> dict;

    // Tree that sorts by values
    private final TreeSet<Key> valuesTree;

    // Memorised keys. Used to find important keys and their corresponding values in O(1)
    private ArrayList<Key> memorisedKeys;

    /**
     * Set up both HashSet and TreeSet and the way to sort TreeSet by its values.
     * O(n * log n) to construct
     * @author Yucheng Zhu
     * @param map The data with keys and their corresponding values
     */
    public DictTree(HashMap<Key, Value> map) {
        this.dict = map;
        // Instructs TreeSet to sort by values instead of keys
        LinkedComparator<Key, Value> byValuesComparator = new LinkedComparator<>(map);
        valuesTree = new TreeSet<>(byValuesComparator);
        valuesTree.addAll(map.keySet());
    }

    public HashMap<Key, Value> getDict() {
        return dict;
    }

    public TreeSet<Key> getValuesTree() {
        return valuesTree;
    }

    /**
     * Set Important keys to be found later in O(1)
     * O(n) to put n keys
     * @author Yucheng Zhu
     * @param memorisedKeys Important keys to be found later in O(1)
     */
    public void setMemorisedKeys(ArrayList<Key> memorisedKeys) {
        this.memorisedKeys = memorisedKeys;
    }

    /**
     * Get group number partitioned by memorised keys' corresponding values
     * Requirement: memorised keys' corresponding values are in sorting order
     * O(g) where g is the number of groups or memorised keys
     * @author Yucheng Zhu
     * @param key Key to get group number
     * @return The group number
     */
    public int getGroup(Key key) {
        int groupNumber = 0; // first group is 0
        for (Key memorisedKey: memorisedKeys) {
            if (dict.get(key).doubleValue() < dict.get(memorisedKey).doubleValue()) {
                return groupNumber; // key is in the current group
            } else {
                groupNumber++; // key is in one of the next groups
            }
        }
        return groupNumber; // key is in the largest group
    }

    /**
     * Get an important keys and its corresponding value
     * O(1) to find a key and its corresponding value
     * @author Yucheng Zhu
     * @param memorisedKeys Important keys to be found later in O(1)
     */
    public ArrayList<Key> getMemorisedKeys(ArrayList<Key> memorisedKeys) {
        return memorisedKeys;
    }

    /**
     * Add a key-value pair to the preference values.
     * O(log n)
     * @author Yucheng Zhu
     * @param keyToAdd Key to add to preferenceValue
     * @param valueToAdd The Corresponding value to add to preferenceValues
     */
    public void put(Key keyToAdd, Value valueToAdd) {
        dict.put(keyToAdd, valueToAdd);
        valuesTree.add(keyToAdd);
    }

    /**
     * Delete a key and its corresponding value from the preference values.
     * O(log n)
     * @author Yucheng Zhu
     * @param keyToDelete Key to be deleted from the preferenceValues
     */
    public void remove(Key keyToDelete) {
        valuesTree.remove(keyToDelete);
        dict.remove(keyToDelete);
    }

    /**
     * Change a value and maintain its sorted order by its corresponding value.
     * O(log n)
     * @author Yucheng Zhu
     * @param key Key whose corresponding value will be updated
     * @param newValue Value to be changed
     */
    public void updateValue(Key key, Value newValue) {
        // reinsert the key, remove it before its corresponding value changes
        valuesTree.remove(key);

        // Change the key's corresponding value
        dict.put(key, newValue);

        // reinsert the key, reinsert it back after its corresponding value changes
        valuesTree.add(key);
    }

    /**
     * Get any value by its key
     * in O(1)
     * @author Yucheng Zhu
     * @return The key with its corresponding key in a key-value pair
     */
    public Value get(Key keyToFind) {
        return dict.get(keyToFind);
    }

    /**
     * Get the smallest value and its corresponding key from the preference values.
     * O(log n)
     * @author Yucheng Zhu
     * @return The smallest value with its corresponding key in a key-value pair
     */
    public NumbersPair<Key, Value> smallest() {
        Key smallestKey = valuesTree.first();
        Value smallestValue = dict.get(smallestKey);
        return new NumbersPair<>(smallestKey, smallestValue);
    }

    /**
     * Get the smallest value and its corresponding key from the preference values.
     * O(log n)
     * @author Yucheng Zhu
     * @return The smallest value with its corresponding key in a key-value pair
     */
    public NumbersPair<Key, Value> largest() {
        Key largestKey = valuesTree.last();
        Value largestValue = dict.get(largestKey);
        return new NumbersPair<>(largestKey, largestValue);
    }

    @Override
    public String toString() {
        return "DictTree{" +
                "dict=" + dict +
                "\n" +
                "valuesTree=" + valuesTree +
                '}';
    }
}
