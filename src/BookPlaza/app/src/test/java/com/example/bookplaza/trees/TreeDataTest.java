package com.example.bookplaza.trees;

import com.example.bookplaza.trees.AVLTree;
import com.example.bookplaza.trees.EmptyNode;
import com.example.bookplaza.trees.TreeData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Yucheng Zhu
 * Test TreeData
 */
public class TreeDataTest {
    // Create an empty tree
    AVLTree<Integer> newAvl;
    AVLTree<Integer> normalAvl;

    /**
     * @author Yucheng Zhu
     * Construct AVL trees to test
     */
    @Before
    public void init() {
        // create an empty avl tree
        newAvl = new AVLTree<>(new EmptyNode<Integer>());

        // Create a filled tree
        normalAvl = new AVLTree<>(new EmptyNode<Integer>());

        normalAvl.insert(new TreeData<>(5, 1));
        normalAvl.insert(new TreeData<>(3, 2));
        normalAvl.insert(new TreeData<>(7, 3));
        normalAvl.insert(new TreeData<>(2, 4));
        normalAvl.insert(new TreeData<>(1, 5));
        normalAvl.insert(new TreeData<>(8, 6));
        normalAvl.insert(new TreeData<>(9, 7));

    }

    /**
     * Test print the correct string
     * @author Yucheng Zhu
     */
    @Test
    public void testToString() {
        assertEquals(
                "TreeData{" +
                        "sortable=" + '5' +
                        ", id='" + '1' + '\'' +
                        '}',
                normalAvl.find(new TreeData<>(5, 1)).getContent().toString()
        );
    }

    /**
     * Test TreeData has the correct hash code
     * @author Yucheng Zhu
     */
    @Test
    public void testHashCode() {
        assertTrue(
                normalAvl.find(new TreeData<>(5, 1)).getContent().hashCode() > 0
        );
    }

    /**
     * @author Yucheng Zhu
     * Find the node containing the TreeData
     */
    @Test
    public void testFindDataByTreeData() {
        assertEquals(
                new TreeData<>(5, 1),
                normalAvl.find(new TreeData<>(5, 1)).getContent()
        );
    }

    /**
     * @author Yucheng Zhu
     * Find the node by the compariable value
     */
    @Test
    public void testFindDataByCompariableValue() {
        assertEquals(
                new TreeData<>(5, 1),
                normalAvl.find(5).getContent()
        );
    }
}
