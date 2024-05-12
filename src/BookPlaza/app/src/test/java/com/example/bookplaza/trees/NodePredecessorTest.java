package com.example.bookplaza.trees;

import com.example.bookplaza.trees.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test AVLNode
 * @author Yucheng Zhu
 */
public class NodePredecessorTest {
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
     * Test finding the predecessor of a node.
     * @author Yucheng Zhu
     */
    @Test
    public void testPredecessor() {
        Node<Integer> node = normalAVL.find(2);
        assertEquals(new TreeData<>(2, 4), node.getContent());

        Node<Integer> predecessor = node.predecessor();
        assertEquals(new TreeData<>(1, 5), predecessor.getContent());
    }

    /**
     * Test finding the predecessor of a node when the node has no right node.
     * @author Yucheng Zhu
     */
    @Test
    public void testPredecessorNoRightBranch() {
        Node<Integer> node = normalAVL.find(3);
        assertEquals(new TreeData<>(3, 2), node.getContent());

        Node<Integer> predecessor = node.predecessor();
        assertEquals(new TreeData<>(2, 4), predecessor.getContent());
    }

    /**
     * Test finding the predecessor of a node
     * when the node has no right node and whose parent is larger.
     * @author Yucheng Zhu
     */
    @Test
    public void testPredecessorNoRightBranchParentIsLarger() {
        Node<Integer> node = sortedAVL.find(7);
        assertEquals(new TreeData<>(7, 17), node.getContent());

        Node<Integer> predecessor = node.predecessor();
        assertEquals(new TreeData<>(6, 16), predecessor.getContent());
    }

    /**
     * Test finding the predecessor of the smallest node.
     * The return value is an empty node, because the smallest node has no predecessor.
     * @author Yucheng Zhu
     */
    @Test
    public void testNullPredecessor() {
        Node<Integer> node = normalAVL.find(1);
        assertEquals(new TreeData<>(1, 5), node.getContent());

        Node<Integer> predecessor = node.predecessor();
        assertTrue(predecessor instanceof EmptyNode);
    }
}
