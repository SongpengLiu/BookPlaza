package com.example.bookplaza.trees;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test BST deletion
 * @author Yucheng Zhu
 */
public class BSTDeletionTest {
    // -- The trees to test
    // An empty tree
    BST<Integer> newBst;
    // An inserted tree
    BST<Integer> normalBst;
    // A pathological case
    BST<Integer> sortedBst;

    // -- A reference to the root
    AVLNode<Integer> newBstRoot;

    /**
     * Create a new tree to test
     * @author Yucheng Zhu
     */
    @Before
    public void init() {
        // Create an empty tree
        newBst = new BST<>(new EmptyNode<Integer>());
        newBstRoot = null;

        // Create a filled tree
        normalBst = new BST<>(new EmptyNode<Integer>());
        normalBst.insert(new TreeData<>(5, 1));
        normalBst.insert(new TreeData<>(3, 2));
        normalBst.insert(new TreeData<>(7, 3));
        normalBst.insert(new TreeData<>(2, 4));
        normalBst.insert(new TreeData<>(1, 5));
        normalBst.insert(new TreeData<>(8, 6));
        normalBst.insert(new TreeData<>(9, 7));

        // Create a pathological case
        sortedBst = new BST<>(new EmptyNode<Integer>());
        sortedBst.insert(new TreeData<>(1, 11));
        sortedBst.insert(new TreeData<>(2, 12));
        sortedBst.insert(new TreeData<>(3, 13));
        sortedBst.insert(new TreeData<>(4, 14));
        sortedBst.insert(new TreeData<>(5, 15));
        sortedBst.insert(new TreeData<>(6, 16));
    }

    /**
     * Test delete a node from BST.
     * @author Yucheng Zhu
     */
    @Test
    public void testDelete() {
        // check node 1 exists
        assertEquals(5, normalBst.find(1).getContent().getId());
        // check node 1 is node 2's left child
        assertEquals(1, (int) normalBst.find(2).getLeft().getContent().getComparable());
        assertEquals(5, (int) normalBst.find(2).getLeft().getContent().getId());

        // delete node 1
        normalBst.delete(new TreeData<>(1, 5));

        // Check node 1 is deleted
        assertNull(normalBst.find(1).getContent());

        // delete node 9
        normalBst.delete(new TreeData<>(9, 7));
        assertNull(normalBst.find(9).getContent());

        // delete node 5
        normalBst.delete(new TreeData<>(5, 1));
        assertNull(normalBst.find(5).getContent());

        // delete node 3
        normalBst.delete(new TreeData<>(3, 2));
        assertNull(normalBst.find(3).getContent());

        // delete node 7
        normalBst.delete(new TreeData<>(7, 3));
        assertNull(normalBst.find(7).getContent());

        // delete node 2
        normalBst.delete(new TreeData<>(2, 4));
        assertNull(normalBst.find(2).getContent());

        // delete node 8
        normalBst.delete(new TreeData<>(8, 6));
        assertNull(normalBst.find(8).getContent());

    }
}
