package com.example.bookplaza.trees;

/**
 * @author Yucheng Zhu
 * An AVL Tree implmentation. An upgrade of BST.
 * Guarentees O(log n)
 * Comparing to Red-Black tree, it does more sorting during insertion and deletion, but can be looked-up more quickly.
 *
 * @param <D> Comparable data
 */
public class AVLTree<D extends Comparable<D>> extends BST<D> {

    public AVLTree(Node<D> root) {
        super(root);
    }

    /**
     * @author Yucheng Zhu
     * Right rotate a node
     * @param root The tree root
     * @param p The p node in right rotation
     * @param q The q node in right rotation
     * @return Parent node after rebalancing
     */
    public Node<D> rightRotate(Node<D> root, Node<D> p, Node<D> q) {
        Node<D> b = p.right;

        if (!(b instanceof EmptyNode)) {
            b.parent = q;
        }
        q.left = b;

        p.right = q;
        if (!(q.parent instanceof EmptyNode)) {
            if (q.equals(q.parent.left)) {
                q.parent.left = p;
            } else {
                q.parent.right = p;
            }
        }
        p.parent = q.parent;
        q.parent = p;

        ((AVLNode<D>) p).updateHeight();
        ((AVLNode<D>) q).updateHeight();

        if (q == root) {
            return p;
        } else {
            return root;
        }
    }

    /**
     * @author Yucheng Zhu
     * Left rotate a node
     * @param root The tree root
     * @param p The p node in left rotation
     * @param q The q node in left rotation
     * @return Parent node after rebalancing
     */
    public Node<D> leftRotate(Node<D> root, Node<D> p, Node<D> q) {
        Node<D> b = q.left;

        if (!(b instanceof EmptyNode)) {
            b.parent = p;
        }
        p.right = b;

        q.left = p;
        if (!(p.parent instanceof EmptyNode)) {
            if (p == p.parent.left) {
                p.parent.left = q;
            } else {
                p.parent.right = q;
            }
        }
        q.parent = p.parent;
        p.parent = q;

        ((AVLNode<D>) p).updateHeight();
        ((AVLNode<D>) q).updateHeight();

        if (p == root) {
            return q;
        } else {
            return root;
        }
    }


    /**
     * @author Yucheng Zhu
     * Rebalance an AVL tree
     * @param root The tree root
     * @param node Node to be rebalanced
     * @return Node after rebalancing
     */
    public Node<D> rebalance(Node<D> root, Node<D> node) {
        while (!(node instanceof EmptyNode)) {
            node.updateHeight();
            if (node.getBalanceFactor() <= -2) {
                if (node.left.getBalanceFactor() <= 0) {
                    root = rightRotate(root, node.left, node);
                } else {
                    root = leftRotate(root, node.left, node.left.right);
                    root = rightRotate(root, node.left, node);
                }
            } else if (node.getBalanceFactor() >= 2) {
                if (node.right.getBalanceFactor() >= 0) {
                    root = leftRotate(root, node, node.right);
                } else {
                    root = rightRotate(root, node.right.left, node.right);
                    root = leftRotate(root, node, node.right);
                }
            }

            node = node.parent;
        }

        return root;
    }

    /**
     * @param data Data to insert
     * @author Yucheng Zhu
     * Insert a data into the tree.
     * Guarantees O(log n)
     */
    @Override
    public Node<D> insert(TreeData<D> data) {
        // BST insertion first
        Node<D> node = super.insert(data);

        // Rebalance the tree to guarantee the O(log n) performance
        this.root = rebalance(this.getRoot(), node);
        return node;
    }

    /**
     * @param dataToDelete The value to be deleted
     * @return Node containing the data deleted
     * @author Yucheng Zhu
     * Delete from the tree
     */
    @Override
    public Node<D> delete(TreeData<D> dataToDelete) {
        AVLTree<D> tree = this;
        // node = delete(tree, key)
        Node<D> node = super.delete(dataToDelete);
        // node.parent is actually the old parent of the node,
        // which is the first potentially out-of-balance node.
        if (!node.isEmpty(node)) {
            tree.root = rebalance(tree.root, node.parent);
        }
        return node;
    }
}
