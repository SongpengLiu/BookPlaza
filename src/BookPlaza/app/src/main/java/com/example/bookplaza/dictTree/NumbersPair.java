package com.example.bookplaza.dictTree;

import java.util.Objects;

/**
 * Standard Pair class, except key and value must both be numbers
 * Pairs are immutable, subsets of tuples.
 * Construct a new NumbersPair for a different value
 * @author Yucheng Zhu
 * @param <K> Key type, must be a number
 * @param <V> Value type, must be a number
 */
public class NumbersPair<K extends Number, V extends Number> {
    // Pairs are immutable, subsets of tuples
    private final K key;
    private final V value;

    /**
     * Constructor puts two numbers into it
     * @author Yucheng Zhu
     * @param key Pair key
     * @param value Pair value
     */
    public NumbersPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get pair key
     * @author Yucheng Zhu
     */
    public K getKey() {
        return key;
    }

    /**
     * Get pair value
     * @author Yucheng Zhu
     */
    public V getValue() {
        return value;
    }

    /**
     * pair1 equals pair2 iff both their keys and values are equal.
     * @author Yucheng Zhu
     */
    @Override
    public boolean equals(Object o) {
        // Edge cases
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // Cast other object
        NumbersPair<?, ?> pair = (NumbersPair<?, ?>) o;
        // Both keys and values must be equal
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    /**
     * Standard hashing
     * @author Yucheng Zhu
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    /**
     * Standard toString, used for printing
     * @author Yucheng Zhu
     */
    @Override
    public String toString() {
        return "NumbersPair{" + key + ", " + value + "}";
    }
}
