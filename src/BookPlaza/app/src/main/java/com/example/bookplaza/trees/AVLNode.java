package com.example.bookplaza.trees;

/**
 * An AVL tree's node.
 * In addition to BST node's features, it also contains a height.
 * @author Yucheng Zhu
 * @param <D> Comparable data
 */
public class AVLNode <D extends Comparable<D>> extends Node<D> {

    private AVLNode<D> root;

    /**
     * Construct a new AVL tree node
     * @author Yucheng Zhu
     * @param content Content
     */
    public AVLNode(TreeData<D> content) {
        super(content, new EmptyNode<>(), new EmptyNode<>(), new EmptyNode<>());
    }

    public AVLNode() {
        super(null, new EmptyNode<>(), new EmptyNode<>(), new EmptyNode<>());
    }
}
