package com.example.bookplaza.trees;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Yucheng Zhu
 * A tree node for BST, AVL tree and Red-Black tree.
 * It contains comparable content, its left, right and parent codes.
 * @param <D> Comparable data
 */
public abstract class Node<D extends Comparable<D>> {
    TreeData<D> content;
    Node<D> left = null;
    Node<D> right = null;
    Node<D> parent = null;
    int height;

    /**
     * @author Yucheng Zhu
     * Create a non-empty node
     */
    public Node(TreeData<D> content, Node<D> left, Node<D> right, Node<D> parent) {
        this.content = content;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    /**
     * @author Yucheng Zhu
     * Create an empty node
     */
    public Node() {}

    public TreeData<D> getContent() {
        return content;
    }

    public void setContent(TreeData<D> content) {
        this.content = content;
    }

    public Node<D> getLeft() {
        return left;
    }

    public void setLeft(Node<D> left) {
        this.left = left;
    }

    public Node<D> getRight() {
        return right;
    }

    public void setRight(Node<D> right) {
        this.right = right;
    }

    public Node<D> getParent() {
        return parent;
    }

    public void setParent(Node<D> parent) {
        this.parent = parent;
    }

    /**
     * Check whether a given node is empty
     * @author Yucheng Zhu
     * @return True if it is empty. Otherwise, false.
     */
    public boolean isEmpty(Node<D> node) {
        return (
                node == null ||
                        (node instanceof EmptyNode)
        );
    }

    /**
     * Get the smallest node in the tree
     * @author Yucheng Zhu
     * @return The smallest node
     */
    public Node<D> findMin() {
        Node<D> node = this;
        while (!isEmpty(node.getLeft())) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Get the largest node in the tree
     * @author Yucheng Zhu
     * @return The largest node
     */
    public Node<D> findMax() {
        Node<D> node = this;
        while (!isEmpty(node.getRight())) {
            node = node.getRight();
        }
        return node;
    }


    /**
     * Return the predecessor node of the given node
     * @author Yucheng Zhu
     * @return The predecessor node
     */
    public Node<D> predecessor() {
        Node<D> node = this;

        // check the Largest item in the left branch
        if (!isEmpty(node.getLeft())) {
            return node.getLeft().findMax();
        }

        // if none in the right branch, check the parent
        Node<D> current = node;

        // If it's the root, return empty node
        // otherwise, return the first ancestor smaller than self
        while (!isEmpty(current.getParent()) &&
                current == current.getParent().getLeft()) {
            current = current.getParent();
        }
        return current.getParent();
    }

    /**
     * Return the successor node of the given node
     * @author Yucheng Zhu
     * @return The successor node
     */
    public Node<D> successor() {
        Node<D> node = this;

        // check the smallest item in the right branch
        if (!isEmpty(node.getRight())) {
            return node.getRight().findMin();
        }

        // if none in the right branch, check the parent
        Node<D> current = node;

        // If it's the root, return empty node
        // otherwise, return the first ancestor smaller than self
        while (!isEmpty(current.getParent()) &&
                current == current.getParent().getRight()) {
            current = current.getParent();
        }
        return current.getParent();
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * Update the height for this node
     * @author Yucheng Zhu
     */
    public void updateHeight() {
        this.height = Math.max(
                this.left.getHeight(),
                this.right.getHeight()
        ) + 1;
    }

    /**
     * Get the balance factor
     * @author Yucheng Zhu
     */
    public int getBalanceFactor() {
        return this.getRight().getHeight() - this.getLeft().getHeight();
    }

    @NotNull
    @Override
    public String toString() {
        return "AVLNode{" +
                "content=" + content+
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(content, node.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
