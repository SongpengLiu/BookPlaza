package com.example.bookplaza.trees;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test EmptyNode
 * @author Yucheng Zhu
 */
public class EmptyNodeTest {

    /**
     * Test empty node's min and max nodes are null by definition
     * @author Yucheng Zhu
     */
    @Test
    public void testEmptyNodeMinMax() {
        EmptyNode<Integer> n = new EmptyNode<>();
        assertNull(n.findMax());
        assertNull(n.findMin());
    }

    @Test
    public void testHashing() {
        EmptyNode<Integer> n = new EmptyNode<>();
        assertTrue((Integer)n.hashCode() instanceof Integer);
    }
}
