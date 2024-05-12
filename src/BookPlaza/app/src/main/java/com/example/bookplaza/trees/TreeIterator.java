package com.example.bookplaza.trees;

import java.util.NoSuchElementException;

/**
 * Iterates through the tree in sorted order.
 * @author Yucheng Zhu
 * @param <D> Comparable data
 */
public class TreeIterator<D extends Comparable<D>> {
    private Node<D> nextNode;

    public TreeIterator(Node<D> lowerBoundNode) {
        this.nextNode = lowerBoundNode;
    }

    /**
     * Check if the node has next
     * @author Yucheng Zhu
     * @return Next node in sorted order
     */
    public boolean hasNext() {
        return nextNode != null;
    }

    /**
     * Get the next node.
     * @author Yucheng Zhu
     * @return Next node in sorted order
     */
    public TreeData<D> next() {
        if (!hasNext()) throw new NoSuchElementException();
        TreeData<D> content = nextNode.getContent();
        nextNode = nextNode.successor();
        return content;
    }
}
