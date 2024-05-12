package com.example.bookplaza.trees;

import java.util.ArrayList;

/**
 * @author Yucheng Zhu
 * A Tree interface. Defining the signature for BST, AVL tree, Red-black tree and B-tree
 * @param <D> Comparable data inside TreeData
 */
public interface Tree<D extends Comparable<D>> {

    /**
     * @author Yucheng Zhu
     * Insert a data into the tree
     * @param data Data to insert
     */
    Node<D> insert(TreeData<D> data);

    /**
     * @author Yucheng Zhu
     * Delete from the tree
     * @param dataToDelete The value to be deleted
     * @return Node containing the data deleted
     */
    Node<D> delete(TreeData<D> dataToDelete);

    /**
     * @author Yucheng Zhu
     * Find a value in the tree and return the node containing the value
     * If not found, return null
     * @param dataToFind The value to be found
     * @return Node containing the data found. Return null if not found
     */
    Node<D> find(TreeData<D> dataToFind);

    /**
     * @author Yucheng Zhu
     * An iterator to iterate through all values
     * @param lowerBoundValue The lower-bound value
     * @param upperBoundValue The upper-bound value
     * @return The data between the lower-bound and upper-bound values
     */
    TreeIterator<D> iterator(TreeData<D> lowerBoundValue, TreeData<D> upperBoundValue);

    /**
     * @author Yucheng Zhu
     * Given the lower-bound value and upper-bound value, find the data in between. Return the sorted data
     * This is slow and should only be used for the prototype and testing
     * Search time: O(n log n): n=1000000
     * Search memory: O(n): n=1000000
     * @param lowerBoundValue The lower-bound value
     * @param upperBoundValue The upper-bound value
     * @return The data between the lower-bound and upper-bound values
     */
    ArrayList<TreeData<D>> between(TreeData<D> lowerBoundValue, TreeData<D> upperBoundValue);
}
