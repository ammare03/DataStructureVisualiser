package datastructures.nonlinnear;

import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

import java.util.UUID;

public class BinarySearchTree<T extends Comparable<T>> extends BaseTree<T> {
    public BinarySearchTree(T root) {
        super(root);
    }

    public boolean search(T data) {
        return searchHelper(data, root);
    }

    private boolean searchHelper(T data, Node<T> root) {
        if (root == null) {
            return false;
        }
        if (data.compareTo(root.data) < 0) {
            return searchHelper(data, root.left);
        } else if (data.compareTo(root.data) > 0) {
            return searchHelper(data, root.right);
        } else {
            return true;
        }
    }

    public UUID insert(T data) {
        return insertHelper(data, root);
    }

    private UUID insertHelper(T data, Node<T> root) {
        if (data.compareTo(root.data) < 0) {
            if (root.left != null) {
                return insertHelper(data, root.left);
            }
            UUID id = UUID.randomUUID();
            root.left = new Node<>(id, data, null, null);
            return id;
        } else if (data.compareTo(root.data) > 0) {
            if (root.right != null) {
                return insertHelper(data, root.right);
            }
            UUID id = UUID.randomUUID();
            root.right = new Node<>(id, data, null, null);
            return id;
        } else {
            throw new IllegalArgumentException("Data already exists!");
        }
    }

    public void remove(UUID id) throws NotImplementedException {
        throw new NotImplementedException("Not yet implemented");
    }
}
