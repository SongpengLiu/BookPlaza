package com.example.bookplaza.trees;

import com.example.bookplaza.trees.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test BST
 * @author Yucheng Zhu
 */
public class BSTTest {
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
     * Test BST set root.
     * Useful for getting a subtree.
     * WARNING: think twice before doing that. CAN cause integration bugs.
     * @author Yucheng Zhu
     */
    @Test
    public void testSetRoot() {
        // Insert root
        newBst.insert(new TreeData<>(5, 1));
        AVLNode<Integer> root1 = (AVLNode<Integer>) newBst.getRoot();

        // Assign an arbitrary node as root. Useful for getting a subtree.
        // WARNING: think twice before doing that.
        newBst.insert(new TreeData<>(3, 2));
        AVLNode<Integer> leftNode = (AVLNode<Integer>) newBst.getRoot();

        newBst.setRoot(leftNode);
        assertEquals(leftNode, newBst.getRoot());
    }

    /**
     * Test BST insertion
     * @author Yucheng Zhu
     */
    @Test
    public void testInsert() {
        // check empty tree before insertion
        assertTrue(newBst.getRoot() instanceof EmptyNode);
        assertNull(newBst.getRoot().getContent());
        assertNull(newBst.getRoot().getLeft());
        assertNull(newBst.getRoot().getRight());
        assertNull(newBst.getRoot().getParent());

        // -- Insert root
        newBst.insert(new TreeData<>(5, 1));
        newBstRoot = (AVLNode<Integer>) newBst.getRoot();
        assertEquals(newBstRoot, newBst.getRoot());
        assertTrue(newBstRoot.getLeft() instanceof EmptyNode);

        // Check parent of an empty node
        assertNull(newBstRoot.getLeft().getParent());

        // Undefined node's left and right are null
        assertNull(newBstRoot.getLeft().getLeft());
        assertNull(newBstRoot.getLeft().getRight());

        // Undefined nodes are EmptyNodes
        assertTrue(newBstRoot.getRight() instanceof EmptyNode);

        // Parent of the root is EmptyNode
        assertTrue(newBstRoot.getParent() instanceof EmptyNode);

        // Undefined nodes' left and right are nulls
        assertTrue(newBstRoot.getRight() instanceof EmptyNode);
        assertTrue(newBstRoot.getParent() instanceof EmptyNode);

        // -- insert to the left subtree
        newBst.insert(new TreeData<>(3, 2));
//        assertEquals(3, (TreeData<Integer>) newBstRoot.getLeft().getContent());
        // Check the parent of a defined node
        assertEquals(newBstRoot, newBstRoot.getLeft().getParent());
//        assertEquals(5, (int) newBstRoot.getLeft().getParent().getContent());

        // -- insert to the right subtree
        newBst.insert(new TreeData<>(7, 3));
        assertEquals(newBstRoot, newBst.getRoot());

        // -- insert two nodes to the left subtree
        newBst.insert(new TreeData<>(2, 4));
        newBst.insert(new TreeData<>(1, 5));

        // -- insert two nodes to the right subtree
        newBst.insert(new TreeData<>(8, 6));
        newBst.insert(new TreeData<>(9, 7));
    }

    /**
     * Test BST look up
     * @author Yucheng Zhu
     */
    @Test
    public void testFind() {
        // -- Find root
        assertEquals(new TreeData<>(5, 1), normalBst.find(new TreeData<>(5, 1)).getContent());
        assertEquals(new TreeData<>(5, 1), normalBst.find(5).getContent());
        // Check its relatives
        assertTrue(normalBst.find(5).getParent() instanceof EmptyNode);
        assertEquals(new TreeData<>(3, 2), normalBst.find(5).getLeft().getContent());
        assertEquals(new TreeData<>(7, 3), normalBst.find(5).getRight().getContent());

        // -- Find a node
        assertEquals(new TreeData<>(3, 2), normalBst.find(3).getContent());
        // Check its relatives
        assertEquals(new TreeData<>(5, 1), normalBst.find(3).getParent().getContent());
        assertEquals(new TreeData<>(2, 4), normalBst.find(3).getLeft().getContent());
        assertNull(normalBst.find(3).getRight().getContent());

        // -- Find more nodes
        assertEquals(new TreeData<>(7, 3), normalBst.find(7).getContent());

        // -- Find double left
        assertEquals(new TreeData<>(2, 4), normalBst.find(2).getContent());
        assertEquals(new TreeData<>(1, 5), normalBst.find(1).getContent());

        // -- Find double right
        assertEquals(new TreeData<>(8, 6), normalBst.find(8).getContent());
        assertEquals(new TreeData<>(9, 7), normalBst.find(9).getContent());

        // -- Find non-existent value
        assertTrue(normalBst.find(10) instanceof EmptyNode);
    }

    /**
     * Test find min
     * @author Yucheng Zhu
     */
    @Test
    public void testFindMin() {
        assertEquals(new TreeData<>(1, 5), normalBst.findMin().getContent());
    }

    /**
     * Test find max
     * @author Yucheng Zhu
     */
    @Test
    public void testFindMax() {
        assertEquals(new TreeData<>(9, 7), normalBst.findMax().getContent());
    }

    /**
     * Test between
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween() {
        assertEquals(
                Arrays.asList(new TreeData[] {
                        new TreeData<>(2, 12),
                        new TreeData<>(3, 13),
                        new TreeData<>(4, 14)
                }),
                sortedBst.between(new TreeData<>(2, 12), new TreeData<>(4, 14))
        );
    }

    /**
     * Test find the closest larger by non-existent value
     * @author Yucheng Zhu
     */
    @Test
    public void testFindClosestLargerByNonExistentValue() {

        System.out.println("testFindClosestLargerByNonExistentValue(): normalBst.find(4)="+normalBst.find(4));
        System.out.println("testFindClosestLargerByNonExistentValue(): normalBst.find(4).getParent()="+normalBst.find(4).getParent());

        assertEquals(
                new TreeData<>(5, 1),
                normalBst.find(4, TreeResultOperator.EQUAL_OR_LARGER).getContent()
        );
    }

    /**
     * Test between, find range by their comparable values
     * @author Yucheng Zhu
     */
    @Test
    public void testBetweenByComparable() {
        assertEquals(
                Arrays.asList(new TreeData[] {
                        new TreeData<>(2, 12),
                        new TreeData<>(3, 13),
                        new TreeData<>(4, 14)
                }),
                sortedBst.between(
                        2, 4,
                        TreeResultOperator.EQUAL, TreeResultOperator.EQUAL
                )
        );
    }

    /**
     * Test between, but the beginning and end values are not in the tree
     * @author Yucheng Zhu
     */
    @Test
    public void testBetweenWithoutExactValues() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(5, 1)
                }),
                normalBst.between(
                        4, 6,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }
}
