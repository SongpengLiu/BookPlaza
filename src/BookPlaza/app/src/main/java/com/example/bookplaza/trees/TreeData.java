package com.example.bookplaza.trees;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Tree data which can be sorted by a number.
 * It also has an id that can be used to know more about that data.
 * @author Yucheng Zhu
 * @param <N> A number used to sort the tree
 */
public class TreeData<N extends Comparable<N>> implements Comparable<TreeData<N>> {

    // Value used to sort. e.g. Book's price
    private final N comparable;

    // id used to find the object. e.g. Book's ISBN
    private final int id;

    public TreeData(N comparable, int id) {
        this.comparable = comparable;
        this.id = id;
    }

    public N getComparable() {
        return comparable;
    }

    public int getId() {
        return id;
    }

    /**
     * Compare this TreeData with the other TreeData.
     * First compare the comparable value. If it's equal, then compare the two TreeData's ids.
     * @author Yucheng Zhu
     * @param other The other TreeData to be compared
     * @return -1 if this TreeData is smaller than the other TreeData, 0 if equal and 1 if larger
     */
    @Override
    public int compareTo(TreeData<N> other) {
        // first compare the comparable value
//        int comparison = Double.compare(comparable.doubleValue(), other.getComparable().doubleValue());
        int comparison = comparable.compareTo(other.getComparable());
        if (comparison != 0) {
            return comparison;
        }

        // if the values are equal, compare their ids
        return Integer.compare(id, other.getId());
    }

    /**
     * Compare this TreeData with the other TreeData.
     * First compare the comparable value. If it's equal, then compare the two TreeData's ids.
     * @author Yucheng Zhu
     * @param numberToCompare The other number to be compared
     * @return -1 if this TreeData is smaller than the other TreeData, 0 if equal and 1 if larger
     */
    public int compareTo(N numberToCompare) {
        // compare the comparable value
//        return Double.compare(comparable.doubleValue(), (double) numberToCompare);
        return comparable.compareTo(numberToCompare);
    }

    @NotNull
    @Override
    public String toString() {
        return "TreeData{" +
                "sortable=" + comparable +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeData<?> treeData = (TreeData<?>) o;
        return Objects.equals(comparable, treeData.comparable) && Objects.equals(id, treeData.id);
    }

    public boolean equals(N numberToCompare) {
        return Objects.equals(comparable, numberToCompare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparable, id);
    }
}
