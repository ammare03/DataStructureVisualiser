package datastructures.nonlinnear;

import datastructures.StateFull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public abstract class BaseTree<T> implements Traversable<BaseTree.Node<T>>, StateFull {
    protected Node<T> root;

    public BaseTree(T root) {
        this.root = new Node<>(UUID.randomUUID(), root, null, null);
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

    public static class Node<T> implements StateFull {
        protected final UUID id;
        protected T data;
        protected Node<T> left;
        protected Node<T> right;

        protected Node(UUID id, T data, Node<T> left, Node<T> right) {
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

        protected String toString(int level) {
            return "\t|".repeat(level++) + "\b." + data +
                    (left == null ? "" : '\n' + left.toString(level)) +
                    (right == null ? "" : '\n' +  right.toString(level));
        }

        @Override
        public Map<String, String> getState() {
            return Map.of(
                    "data", data.toString(),
                    "left", left == null ? "null" : String.valueOf(left.hashCode()),
                    "right", right == null ? "null" : String.valueOf(right.hashCode())
            );
        }
    }
    @Override
    public String toString() {
        return root.toString(0);
    }

    public Node<T> getRoot() {
        return root;
    }

    @Override
    public Map<String, String> getState() {
        return Map.of("root", String.valueOf(root.hashCode()));
    }
}
