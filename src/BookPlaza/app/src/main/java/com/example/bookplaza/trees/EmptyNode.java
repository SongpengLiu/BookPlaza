package com.example.bookplaza.trees;

/**
 * @author Yucheng Zhu
 * An empty tree node for BST, AVL tree and Red-Black tree.
 * It contains empty comparable content, its left, right and parent codes.
 * @param <D> Comparable data
 */
public class EmptyNode<D extends Comparable<D>> extends Node<D> {
    D content = null;
    Node<D> left = null;
    Node<D> right = null;
    Node<D> parent = null;

    public EmptyNode() {}

    @Override
    /**
     * @author Yucheng Zhu
     * Get the smallest node in the tree
     * @return The smallest node, null by definition
     */
    public Node<D> findMin() {
        return null;
    }

    /**
     * Get the largest node in the tree
     * @return The largest node, null by definition
     * @author Yucheng Zhu
     */
    @Override
    public Node<D> findMax() {
        return null;
    }
}

