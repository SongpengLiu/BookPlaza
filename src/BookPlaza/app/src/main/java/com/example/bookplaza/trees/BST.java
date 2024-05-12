package com.example.bookplaza.trees;

import java.util.ArrayList;

/**
 * A Binary Search Tree implmentation. More importantly, a parent class of AVL tree and Red-Black tree.
 * BST has a pathological case of O(n) when data is sorted.
 * Thus, it should NOT be used. Always use its child classes: AVLTree and RedBlackTree.
 * @author Yucheng Zhu
 * @param <D> Comparable data inside TreeData
 */
public class BST<D extends Comparable<D>> implements Tree<D> {

    Node<D> root;

    public Node<D> getRoot() {
        return root;
    }

    public void setRoot(Node<D> root) {
        this.root = root;
    }

    public BST(Node<D> root) {
        this.root = root;
    }

    /**
     * @author Yucheng Zhu
     * A helper method to insert an item into a subtree
     * @param root_ Root node of the subtree
     * @param inserted Node to insert
     * @param tree The tree
     */
    private void _insert(Node<D> root_, Node<D> inserted, BST<D> tree) {
        if (inserted instanceof EmptyNode) {
            return ;
        }

        // if smaller, insert into the left subtree
        if (inserted.getContent().compareTo(root_.getContent()) < 0) {
            if (root_.left instanceof EmptyNode) {
                inserted.parent = root_;
                root_.left = inserted;
            } else {
                _insert(root_.getLeft(), inserted, tree);
            }

        } else { // if larger or equal, insert into the right subtree
            if (root_.right instanceof EmptyNode) {
                inserted.parent = root_;
                root_.right = inserted;
            } else {
                _insert(root_.right, inserted, tree);
            }
        }
    }

    /**
     * @author Yucheng Zhu
     * Insert a data into the tree
     * @param data Data to insert
     */
    @Override
    public Node<D> insert(TreeData<D> data) {
        BST<D> tree = this;

        if (tree.root instanceof EmptyNode) { // expand EmptyNode token to an empty AVLNode
            tree.root = new AVLNode<>();
        }

        Node<D> root_ = tree.root;
        if (root_.content == null) {
            root_.content = data;
            return root_;
        }

        // Call recursive function
        AVLNode<D> inserted_ = new AVLNode<>(data);
        _insert(root_, inserted_, tree);
        return inserted_;
    }

    /**
     * @param val The value to be found
     * @return Node containing the data found. Return null if not found
     * @author Yucheng Zhu
     * Find a value in the tree and return the node containing the value
     * If not found, return null
     */
//    @Override
    public Node<D> find(TreeData<D> val) {
        return _rFind(root, val);
    }

    /**
     * @param val The value to be found
     * @return Node containing the data found. Return null if not found
     * @author Yucheng Zhu
     * Find a value in the tree and return the node containing the value
     * If not found, return null
     */
    public Node<D> find(D val) {
        return _rFind(root, val);
    }

    public Node<D> find(D val, TreeResultOperator treeResultOperator) {
        return _rFind(root, val, treeResultOperator, false);
    }

    /**
     * @param root_ Root for the subtrees. Usually different from the whole tree.
     * @param val The value to be found
     * @return A recursive helper function to find the node starting from the subtree root
     * @author Yucheng Zhu
     * Find a value in the tree and return the node containing the value
     * If not found, return null
     */
    private Node<D> _rFind(Node<D> root_, TreeData<D> val) {
        if (root_ == null || root_ instanceof EmptyNode) { // -- Key not found in BST
            return root_;
        }
        if (val.compareTo(root_.getContent()) == 0) { // if val == Key
            return root_;
        } else if (val.compareTo(root_.getContent()) < 0) {  // if val < Key
            return _rFind(root_.left, val);
        } else { // if val > Key
            return _rFind(root_.right, val);
        }
    }

    private Node<D> _rFind(Node<D> root_, D val, TreeResultOperator treeResultOperator, boolean found) {
        // return found solution to the parent level of recursion. Only used in non-exact values
        if (found) return root_;

        // start finding for the value
        if (root_ == null || root_ instanceof EmptyNode) { // -- Key not found in BST
            return root_;
        }
        if (val.compareTo(root_.getContent().getComparable()) == 0) { // if val == Key
            return root_;
        } else if (val.compareTo(root_.getContent().getComparable()) < 0) {  // if val < Key
            // left node is empty
            if (root_.isEmpty(root_.left) && (!treeResultOperator.equals(TreeResultOperator.EQUAL))) {
                found = true; // tell the parent recursion that solution is found
                if (treeResultOperator.equals(TreeResultOperator.EQUAL_OR_LARGER)) {
                    return root_.successor();
                } else if (treeResultOperator.equals(TreeResultOperator.EQUAL_OR_SMALLER)) {
                    return root_.predecessor();
                }
            }

            // recursively find in the left subtree
            return _rFind(root_.left, val, treeResultOperator, found);
        } else { // if val > Key
            // right node is empty
            if (root_.isEmpty(root_.right) && (!treeResultOperator.equals(TreeResultOperator.EQUAL))) {
                found = true; // tell the parent recursion that solution is found
                if (treeResultOperator.equals(TreeResultOperator.EQUAL_OR_LARGER)) {
                    return root_.successor();
                } else if (treeResultOperator.equals(TreeResultOperator.EQUAL_OR_SMALLER)) {
                    return root_.predecessor();
                }
            }

            // recursively find in the right subtree
            return _rFind(root_.right, val, treeResultOperator, found);
        }
    }

    private Node<D> _rFind(Node<D> root_, D val) {
        return _rFind(root_, val, TreeResultOperator.EQUAL, false);
    }

    /**
     * @author Yucheng Zhu
     * Find the smallest node in the tree
     * @return The node containing the smallest value in the tree.
     */
    public Node<D> findMin() {
        return this.getRoot().findMin();
    }

    /**
     * @author Yucheng Zhu
     * Find the largest node in the tree
     * @return The node containing the smallest value in the tree.
     */
    public Node<D> findMax() {
        return this.getRoot().findMax();
    }


    /**
     * @param dataToDelete The value to be deleted
     * @return Node containing the data deleted
     * @author Yucheng Zhu
     * Delete from the tree
     */
//    @Override
    public Node<D> delete(TreeData<D> dataToDelete) {
        BST<D> tree = this;
        Node<D> found = tree.find(dataToDelete);
        Node<D> deleted;

        if (!tree.root.isEmpty(found)){
            Node<D>[] nodesChangedInDeletion = _delete(tree.root, found);
            tree.root = nodesChangedInDeletion[0];
            deleted = nodesChangedInDeletion[1];

        } else {
            deleted = new EmptyNode<>();
        }
        return deleted;
    }

    /**
     * A util function to be used by deletion. Recursively deletes
     * @author Yucheng Zhu
     * @param root_ Subtree root
     * @param node Node to be deleted
     * @return new subtree root and the deleted node
     */
    private Node<D>[] _delete(Node<D> root_, Node<D> node) {
        // -- Case 1: left or right branch is empty, graft the non-empty branch to the deleted node
        // This is O(1)
        if (root_.isEmpty(node.left)) {
            root_ = swapChild(root_, node, node.right);
        } else if (root_.isEmpty(node.right)) {
            root_ = swapChild(root_, node, node.left);
        // -- Case 2: both left and right branch are full. Append the left branch to the largest leaf of deleted.parent.left. Graft the right branch to the deleted node.
        } else {
            // This is O(1)
            // -4- call the node to be deleted D. Do not delete! D. Instead, choose either its in-order predecessor node or its in-order successor node as replacement node E
            // --- Replace successor's void
            Node<D> successor = node.right.findMin(); //  -- TODO: Optimization: randomly choose predecessor xor successor
            if (root_.isEmpty(successor.right)) {
                if (successor == successor.parent.left) { // -- most of time
                    successor.parent.left = new EmptyNode<>();
                } else { // -- only when successor is node.right?
                    successor.parent.right = new EmptyNode<>();
                }
            } else {
                // -- If E has a child, say F, it is a right child. Replace E with F at E's parent
                // -- Swap parent's child to the new node (cannot not be the right child)
                Node<D> f = successor.right;
                if (successor == successor.parent.left) {
                    successor.parent.left = f;
                } else {
                    successor.parent.right = f;
                }
                f.parent = successor.parent;
                if (root_.isEmpty(successor.left)) { // This is needed to prevent root.right.right.left from being mistakenly deleted
                    f.left = successor.left;
                }
            }

            // actually swap nodes, so that successor's pointer get the correct new node
            successor.left = node.left;
            successor.right = node.right;
            successor.parent = node.parent;
            if (root_.isEmpty(node.parent)) { // Change root
                root_ = successor;
            }

            if (!root_.isEmpty(node.left)) {
                node.left.parent = successor;
            }
            if (!root_.isEmpty(node.right)) {
                node.right.parent = successor;
            }
            if (!root_.isEmpty(node.parent)) {
                if (node.parent.left == node) {
                    node.parent.left = successor;
                } else {
                    node.parent.right = successor;
                }
            }
            return new Node[] {root_, node};
        }
        return new Node[] {root_, node};
    }

    /**
     * A util function to be used by deletion.
     * @author Yucheng Zhu
     * @param node The node to swap with its child
     * @param newChild The new child after swapping
     */
    private Node<D> swapChild(Node<D> root_, Node<D> node, Node<D> newChild) {
        // Reassign new parent to child
        if (!node.isEmpty(newChild)) {
            newChild.parent = node.parent;
        }
        // Reassign new child to parent
        if (!node.isEmpty(node.parent)) {
            if (node.parent.left == node) {
                node.parent.left = newChild;
            } else if (node.parent.right == node) {
                node.parent.right = newChild;
            }
        } else {
            root_ = newChild;
        }

        return root_;
    }


    /**
     * @author Yucheng Zhu
     * An iterator to iterate through all values
     * @param lowerBoundValue The lower-bound value
     * @param upperBoundValue The upper-bound value
     * @return The data between the lower-bound and upper-bound values
     */
    @Override
    public TreeIterator<D> iterator(TreeData<D> lowerBoundValue, TreeData<D> upperBoundValue) {
        return new TreeIterator<>(find(lowerBoundValue));
    }

    /**
     * @param lowerBoundValue The lower-bound value
     * @param upperBoundValue The upper-bound value
     * @return The data between the lower-bound and upper-bound values
     * This is slow and should only be used for the prototype and testing
     * Search time: O(n log n): n=1000000
     * Search memory: O(n): n=1000000
     * @author Yucheng Zhu
     * Given the lower-bound value and upper-bound value, find the data in between. Return the sorted data
     */
    @Override
    public ArrayList<TreeData<D>> between(TreeData<D> lowerBoundValue, TreeData<D> upperBoundValue) {
        ArrayList<TreeData<D>> sortedNodes = new ArrayList<>();
        TreeIterator<D> it = iterator(lowerBoundValue, upperBoundValue);
        while (it.hasNext()) {
            TreeData<D> data = it.next();
            if (data.compareTo(upperBoundValue) > 0) break;
            sortedNodes.add(data);
        }

        return sortedNodes;
    }

    public ArrayList<TreeData<D>> between(
            D lowerBoundValue,
            D upperBoundValue,
            TreeResultOperator lowerBoundOperator,
            TreeResultOperator upperBoundOperator
    ) {
        ArrayList<TreeData<D>> sortedNodes = new ArrayList<>();

        // add the first value
        Node<D> firstNode = find(lowerBoundValue, lowerBoundOperator);
        System.out.println("currentNode: " + firstNode.getContent().getId());
        if (firstNode.getContent().getComparable().compareTo(lowerBoundValue) > 0 ||
                lowerBoundOperator.equals(TreeResultOperator.EQUAL_OR_LARGER) ||
                lowerBoundOperator.equals(TreeResultOperator.EQUAL)
        ) {
            System.out.println("adding:" + firstNode.getContent().getId() + " "+firstNode.getContent().getComparable());
            sortedNodes.add(firstNode.getContent());
        }


        // add the subsequent values
        Node<D> nextNode = firstNode;
        while (true) {
            nextNode = nextNode.successor();
            if (nextNode.getContent() != null) {
                System.out.println("checking :" +  nextNode.getContent().getId());
            }
            if (firstNode.isEmpty(nextNode) || // no more values
                    nextNode.getContent().compareTo(upperBoundValue) > 0) { // exceeds the upper bound
                break;
            }

            // Omit the values equal to the end boundary
            if (upperBoundOperator.equals(TreeResultOperator.SMALLER) && nextNode.getContent().compareTo(upperBoundValue) == 0) {
                break;
            }

            // Add the value larger than the beginning boundary
            if (nextNode.getContent().getComparable().compareTo(lowerBoundValue) > 0 ||
                    lowerBoundOperator.equals(TreeResultOperator.EQUAL_OR_LARGER) ||
                    lowerBoundOperator.equals(TreeResultOperator.EQUAL)
            ) {
                System.out.println("adding:" + nextNode.getContent().getId() + " "+nextNode.getContent().getComparable());
                sortedNodes.add(nextNode.getContent());
            }
        }

        return sortedNodes;
    }
}
