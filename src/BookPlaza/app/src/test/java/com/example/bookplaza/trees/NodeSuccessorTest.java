package com.example.bookplaza.trees;

import com.example.bookplaza.trees.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test AVLNode
 * @author Yucheng Zhu
 */
public class NodeSuccessorTest {
    // -- The trees to test
    // An empty tree
    AVLTree<Integer> newAVL;
    // An inserted tree
    AVLTree<Integer> normalAVL;
    // A pathological case
    AVLTree<Integer> sortedAVL;

    // -- A reference to the root
    AVLNode<Integer> newAVLRoot;

    /**
     * @author Yucheng Zhu
     * Create a new tree to test
     */
    @Before
    public void init() {
        // Create an empty tree
        newAVL = new AVLTree<>(new EmptyNode<Integer>());
        newAVLRoot = null;

        // Create a filled tree
        normalAVL = new AVLTree<>(new EmptyNode<Integer>());
        normalAVL.insert(new TreeData<>(5, 1));
        normalAVL.insert(new TreeData<>(3, 2));
        normalAVL.insert(new TreeData<>(7, 3));
        normalAVL.insert(new TreeData<>(2, 4));
        normalAVL.insert(new TreeData<>(1, 5));
        normalAVL.insert(new TreeData<>(8, 6));
        normalAVL.insert(new TreeData<>(9, 7));

        // Create a pathological case
        sortedAVL = new AVLTree<>(new EmptyNode<Integer>());
        sortedAVL.insert(new TreeData<>(1, 11));
        sortedAVL.insert(new TreeData<>(2, 12));
        sortedAVL.insert(new TreeData<>(3, 13));
        sortedAVL.insert(new TreeData<>(4, 14));
        sortedAVL.insert(new TreeData<>(5, 15));
        sortedAVL.insert(new TreeData<>(6, 16));
        sortedAVL.insert(new TreeData<>(7, 17));
        sortedAVL.insert(new TreeData<>(8, 18));
        sortedAVL.insert(new TreeData<>(9, 19));
    }

    /**
     * Test finding the successor of a node.
     * @author Yucheng Zhu
     */
    @Test
    public void testSuccessor() {
        Node<Integer> node = normalAVL.find(2);
        assertEquals(new TreeData<>(2, 4), node.getContent());

        Node<Integer> successor = node.successor();
        assertEquals(new TreeData<>(3, 2), successor.getContent());
    }

    /**
     * Test finding the successor of a node
     * when the node has no left node and whose parent is smaller.
     * @author Yucheng Zhu
     */
    @Test
    public void testPredecessorNoRightBranchParentIsLarger() {
        Node<Integer> node = normalAVL.find(3);
        assertEquals(new TreeData<>(3, 2), node.getContent());

        Node<Integer> successor = node.successor();
        assertEquals(new TreeData<>(5, 1), successor.getContent());
    }

    /**
     * Test finding the successor of the largest node.
     * The return value is an empty node, because the smallest node has no predecessor.
     * @author Yucheng Zhu
     */
    @Test
    public void testNullPredecessor() {
        Node<Integer> node = normalAVL.find(9);
        assertEquals(new TreeData<>(9, 7), node.getContent());

        Node<Integer> successor = node.successor();
        assertTrue(successor instanceof EmptyNode);
    }
}
