package com.example.bookplaza.trees;

import com.example.bookplaza.trees.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test AVLTree
 * @author Yucheng Zhu
 */
public class AVLTreeTest {
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
     * @author Yucheng Zhu
     * Test BST set root.
     * Useful for getting a subtree.
     * WARNING: think twice before doing that. CAN cause integration bugs.
     */
    @Test
    public void testSetRoot() {
        // Insert root
        newAVL.insert(new TreeData<>(5, 1));
        AVLNode<Integer> root1 = (AVLNode<Integer>) newAVL.getRoot();

        // Assign an arbitrary node as root. Useful for getting a subtree.
        // WARNING: think twice before doing that.
        newAVL.insert(new TreeData<>(3, 2));
        AVLNode<Integer> leftNode = (AVLNode<Integer>) newAVL.getRoot();

        newAVL.setRoot(leftNode);
        assertEquals(leftNode, newAVL.getRoot());
    }

    /**
     * @author Yucheng Zhu
     * Test BST insertion
     */
    @Test
    public void testInsert() {
        // check empty tree before insertion
        assertTrue(newAVL.getRoot() instanceof EmptyNode);
        assertNull(newAVL.getRoot().getContent());
        assertNull(newAVL.getRoot().getLeft());
        assertNull(newAVL.getRoot().getRight());
        assertNull(newAVL.getRoot().getParent());

        // -- Insert root
        newAVL.insert(new TreeData<>(5, 1));
        newAVLRoot = (AVLNode<Integer>) newAVL.getRoot();
        assertEquals(newAVLRoot, newAVL.getRoot());
        assertTrue(newAVLRoot.getLeft() instanceof EmptyNode);

        // Check parent of an empty node
        assertNull(newAVLRoot.getLeft().getParent());

        // Undefined node's left and right are null
        assertNull(newAVLRoot.getLeft().getLeft());
        assertNull(newAVLRoot.getLeft().getRight());

        // Undefined nodes are EmptyNodes
        assertTrue(newAVLRoot.getRight() instanceof EmptyNode);

        // Parent of the root is EmptyNode
        assertTrue(newAVLRoot.getParent() instanceof EmptyNode);

        // Undefined nodes' left and right are nulls
        assertTrue(newAVLRoot.getRight() instanceof EmptyNode);
        assertTrue(newAVLRoot.getParent() instanceof EmptyNode);

        // -- insert to the left subtree
        newAVL.insert(new TreeData<>(3, 2));
        assertEquals(new TreeData<>(3, 2), newAVLRoot.getLeft().getContent());
        // Check the parent of a defined node
        assertEquals(newAVLRoot, newAVLRoot.getLeft().getParent());
        assertEquals(new TreeData<>(5, 1), newAVLRoot.getLeft().getParent().getContent());

        // -- insert to the right subtree
        newAVL.insert(new TreeData<>(7, 3));
        assertEquals(newAVLRoot, newAVL.getRoot());

        // -- insert two nodes to the left subtree
        newAVL.insert(new TreeData<>(2, 4));
        newAVL.insert(new TreeData<>(1, 5));

        // -- insert two nodes to the right subtree
        newAVL.insert(new TreeData<>(8, 6));
        newAVL.insert(new TreeData<>(9, 7));
    }

    /**
     * @author Yucheng Zhu
     * Test BST look up
     */
    @Test
    public void testFind() {
        // -- Find root
        assertEquals(new TreeData<>(5, 1), normalAVL.find(5).getContent());
        // Check its relatives
        assertTrue(normalAVL.find(5).getParent() instanceof EmptyNode);
        assertEquals(new TreeData<>(2, 4), normalAVL.find(5).getLeft().getContent());
        assertEquals(new TreeData<>(8, 6), normalAVL.find(5).getRight().getContent());

        // -- Find a node
        assertEquals(new TreeData<>(2, 4), normalAVL.find(2).getContent());
        // Check its relatives
        assertEquals(new TreeData<>(5, 1), normalAVL.find(2).getParent().getContent());
        assertEquals(new TreeData<>(1, 5), normalAVL.find(2).getLeft().getContent());
        assertNull(normalAVL.find(new TreeData<>(3, 2)).getRight().getContent());

        // -- Find more nodes
        assertEquals(new TreeData<>(7, 3), normalAVL.find(7).getContent());

        // -- Find double left
        assertEquals(new TreeData<>(2, 4), normalAVL.find(2).getContent());
        assertEquals(new TreeData<>(1, 5), normalAVL.find(1).getContent());

        // -- Find double right
        assertEquals(new TreeData<>(8, 6), normalAVL.find(8).getContent());
        assertEquals(new TreeData<>(9, 7), normalAVL.find(9).getContent());

        // -- Find non-existent value
        assertTrue(normalAVL.find(10) instanceof EmptyNode);
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
                sortedAVL.between(
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
                normalAVL.between(
                        4, 6,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }

    /**
     * Test between, x >= 2 && x <= 4
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween2() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(2, 12),
                        new TreeData<>(3, 13),
                        new TreeData<>(4, 14)
                }),
                sortedAVL.between(
                        2, 4,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }

    /**
     * Test between: x > 2 && x <= 4
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween3() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(3, 13),
                        new TreeData<>(4, 14)
                }),
                sortedAVL.between(
                        2, 4,
                        TreeResultOperator.LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }

    /**
     * Test between: x > 2 && x < 4
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween4() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(3, 13)
                }),
                sortedAVL.between(
                        2, 4,
                        TreeResultOperator.LARGER, TreeResultOperator.SMALLER
                )
        );
    }

    /**
     * Test between: x >= 2 && x < 4
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween5() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(2, 12),
                        new TreeData<>(3, 13)
                }),
                sortedAVL.between(
                        2, 4,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.SMALLER
                )
        );
    }

    /**
     * Test between: x >= 7 && x < inf
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween6() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(7, 17),
                        new TreeData<>(8, 18),
                        new TreeData<>(9, 19),
                }),
                sortedAVL.between(
                        7, Integer.MAX_VALUE,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.SMALLER
                )
        );
    }

    /**
     * Test between: x >= 5 && x <= 5
     * @author Yucheng Zhu
     */
    @Test
    public void testBetween7() {
        assertEquals(
                Arrays.asList(new TreeData[]{
                        new TreeData<>(5, 15),
                }),
                sortedAVL.between(
                        5, 5,
                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.EQUAL_OR_SMALLER
                )
        );
    }

//    /**
//     * Find the smallest value without exact number
//     * @author Yucheng Zhu
//     */
//    @Test
//    public void testFindSmallestInexact() {
//        System.out.println("Integer.MIN_VALUE="+Integer.MIN_VALUE);
//        assertEquals(
//                Arrays.asList(new TreeData[]{
//                        new TreeData<>(1, 11),
//                }),
//                sortedAVL.find(
//                        0,
//                        TreeResultOperator.EQUAL_OR_LARGER
//                )
//        );
//    }

//    /**
//     * Find the smallest value without exact number
//     * @author Yucheng Zhu
//     */
//    @Test
//    public void testFindLargestInexact() {
//        System.out.println("Integer.MIN_VALUE="+Integer.MIN_VALUE);
//        assertEquals(
//                Arrays.asList(new TreeData[]{
//                        new TreeData<>(9, 19),
//                }),
//                sortedAVL.find(
//                        20,
//                        TreeResultOperator.EQUAL_OR_SMALLER
//                )
//        );
//    }

//    /**
//     * Test between: x >= 7 && x < -inf
//     * @author Yucheng Zhu
//     */
//    @Test
//    public void testBetween8() {
//        System.out.println("Integer.MIN_VALUE="+Integer.MIN_VALUE);
//        assertEquals(
//                Arrays.asList(new TreeData[]{
//                        new TreeData<>(1, 11),
//                        new TreeData<>(2, 12),
//                        new TreeData<>(3, 13),
//                }),
//                sortedAVL.between(
//                        0, 4,
////                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.SMALLER
//                        TreeResultOperator.EQUAL_OR_LARGER, TreeResultOperator.SMALLER
//                )
//        );
//    }
}
