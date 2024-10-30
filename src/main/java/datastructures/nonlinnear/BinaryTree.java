package datastructures.nonlinnear;

import datastructures.nonlinnear.BinaryTree.Node;
import exceptions.OverflowException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BinaryTree<T> implements Traversable<Node<T>> {
    private Node<T> root;

    public BinaryTree(T root) {
        this.root = new Node<>(UUID.randomUUID(), root, null, null);
    }

    public UUID assignLeft(T data) {
        UUID newId = UUID.randomUUID();
        root.left = new Node<>(newId, data, null, null);
        return newId;
    }

    public UUID assignRight(T data) {
        UUID newId = UUID.randomUUID();
        root.right = new Node<>(newId, data, null, null);
        return newId;
    }

    public UUID assignLeft(T data, UUID id) {
        UUID newId = UUID.randomUUID();
        for (Node<T> i : this) {
            if (i.id.equals(id)) {
                i.left = new Node<>(newId, data, null, null);
                return newId;
            }
        }
        throw new IllegalArgumentException("No node found with given id");
    }

    public UUID assignRight(T data, UUID id) {
        UUID newId = UUID.randomUUID();
        for (Node<T> i : this) {
            if (i.id.equals(id)) {
                i.right = new Node<>(newId, data, null, null);
                return newId;
            }
        }
        throw new IllegalArgumentException("No node found with given id");
    }

    public void remove(UUID id) {
        if(root.id.equals(id)) {
            throw new UnsupportedOperationException("Cannot remove the root!");
        }
        for (Node<T> i : this) {
            if (i.left != null && i.left.id.equals(id)) {
                i.left = null;
                return;
            } else if (i.right != null && i.right.id.equals(id)) {
                i.right = null;
                return;
            }
        }
        throw new IllegalArgumentException("No node found with given id");
    }

    @Override
    public Iterator<Node<T>> iterator(Traversal traversal) {
        switch (traversal) {
            case INORDER -> {
                return traverseIn(root, new ArrayList<>()).iterator();
            }
            case PREORDER -> {
                return traversePre(root, new ArrayList<>()).iterator();
            }
            case POSTORDER -> {
                return traversePost(root, new ArrayList<>()).iterator();
            } default -> {
                return traversePre(root, new ArrayList<>()).iterator();
            }
        }
    }

    private ArrayList<Node<T>> traversePre(Node<T> node, ArrayList<Node<T>> list) {
        list.add(node);
        if (node.left != null) {
            traversePre(node.left, list);
        }
        if (node.right != null) {
            traversePre(node.right, list);
        }
        return list;
    }

    private ArrayList<Node<T>> traversePost(Node<T> node, ArrayList<Node<T>> list) {
        if (node.left != null) {
            traversePost(node.left, list);
        }
        if (node.right != null) {
            traversePost(node.right, list);
        }
        list.add(node);
        return list;
    }

    private ArrayList<Node<T>> traverseIn(Node<T> node, ArrayList<Node<T>> list) {
        if (node.left != null) {
            traverseIn(node.left, list);
        }
        list.add(node);
        if(node.right != null) {
            traverseIn(node.right, list);
        }
        return list;
    }

    public Node<T> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return root.toString(0);
    }

    public static class Node<T> {
        private final UUID id;
        private final T data;
        private Node<T> left;
        private Node<T> right;

        private Node(UUID id, T data, Node<T> left, Node<T> right) {
            this.id = id;
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public UUID getId() {
            return id;
        }

        public T getData() {
            return data;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        private String toString(int level) {
            return "\t|".repeat(level++) + "\b." + data +
                    (left == null ? "" : '\n' + left.toString(level)) +
                    (right == null ? "" : '\n' +  right.toString(level));
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
