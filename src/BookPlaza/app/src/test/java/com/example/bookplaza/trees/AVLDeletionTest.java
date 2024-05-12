package com.example.bookplaza.trees;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AVLDeletionTest {
    // -- The trees to test
    // An empty tree
    AVLTree<Integer> newAvl;
    // An inserted tree
    AVLTree<Integer> normalAvl;
    // A pathological case
    AVLTree<Integer> sortedAvl;

    // -- A reference to the root
    AVLNode<Integer> newAvlRoot;
    /**
     * Create a new tree to test
     * @author Yucheng Zhu
     */
    @Before
    public void init() {
        // Create an empty tree
        newAvl = new AVLTree<>(new EmptyNode<Integer>());
        newAvlRoot = null;

        // Create a filled tree
        normalAvl = new AVLTree<>(new EmptyNode<Integer>());
        normalAvl.insert(new TreeData<>(5, 1));
        normalAvl.insert(new TreeData<>(3, 2));
        normalAvl.insert(new TreeData<>(7, 3));
        normalAvl.insert(new TreeData<>(2, 4));
        normalAvl.insert(new TreeData<>(1, 5));
        normalAvl.insert(new TreeData<>(8, 6));
        normalAvl.insert(new TreeData<>(9, 7));

        // Create a pathological case
        sortedAvl = new AVLTree<>(new EmptyNode<Integer>());
        sortedAvl.insert(new TreeData<>(1, 11));
        sortedAvl.insert(new TreeData<>(2, 12));
        sortedAvl.insert(new TreeData<>(3, 13));
        sortedAvl.insert(new TreeData<>(4, 14));
        sortedAvl.insert(new TreeData<>(5, 15));
        sortedAvl.insert(new TreeData<>(6, 16));
    }

    /**
     * Test delete a node from BST.
     * @author Yucheng Zhu
     */
    @Test
    public void testDelete() {
        // check node 1 exists
        assertEquals(5, normalAvl.find(1).getContent().getId());
        // check node 1 is node 2's left child
        assertEquals(1, (int) normalAvl.find(2).getLeft().getContent().getComparable());
        assertEquals(5, (int) normalAvl.find(2).getLeft().getContent().getId());

        // delete node 1
        normalAvl.delete(new TreeData<>(1, 5));

        // Check node 1 is deleted
        assertNull(normalAvl.find(1).getContent());

        // delete node 9
        normalAvl.delete(new TreeData<>(9, 7));
        assertNull(normalAvl.find(9).getContent());

        // delete node 5
        normalAvl.delete(new TreeData<>(5, 1));
        assertNull(normalAvl.find(5).getContent());

        // delete node 3
        normalAvl.delete(new TreeData<>(3, 2));
        assertNull(normalAvl.find(3).getContent());

        // delete node 7
        normalAvl.delete(new TreeData<>(7, 3));
        assertNull(normalAvl.find(7).getContent());

        // delete node 2
        normalAvl.delete(new TreeData<>(2, 4));
        assertNull(normalAvl.find(2).getContent());

        // delete node 8
        normalAvl.delete(new TreeData<>(8, 6));
        assertNull(normalAvl.find(8).getContent());
    }
}
