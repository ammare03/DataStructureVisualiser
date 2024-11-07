package datastructures.nonlinnear;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BinarySearchTree extends BaseTree<Integer> {
    public BinarySearchTree(Integer root) {
        super(root);
    }

    @Nullable
    public UUID search(Integer data) {
        return searchHelper(data, root);
    }

    private UUID searchHelper(Integer data, Node<Integer> root) {
        if (root == null) {
            return null;
        }
        if (data.compareTo(root.data) < 0) {
            return searchHelper(data, root.left);
        } else if (data.compareTo(root.data) > 0) {
            return searchHelper(data, root.right);
        } else {
            return root.id;
        }
    }

    public UUID insert(Integer data) {
        return insertHelper(data, root);
    }

    private UUID insertHelper(Integer data, Node<Integer> root) {
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

    public void remove(UUID id) {
        remove(id, Deletion.SUCCESSOR);
    }

    public void remove(UUID id, Deletion method) {
        NodeParent<Integer> nodeParent = getNodeParent(id);
        Node<Integer> node = nodeParent.getChild();
        if (node.left == null && node.right == null) {
            switch (nodeParent.childPosition) {
                case SELF -> throw new IllegalArgumentException("Cannot remove root!");
                case LEFT -> nodeParent.parent.left = null;
                case RIGHT -> nodeParent.parent.right = null;
            }
            return;
        }
        if (node.left == null) {
            switch (nodeParent.childPosition) {
                case LEFT -> nodeParent.parent.left = nodeParent.parent.left.right;
                case RIGHT -> nodeParent.parent.right = nodeParent.parent.right.right;
            }
            return;
        }
        if (node.right == null) {
            switch (nodeParent.childPosition) {
                case LEFT -> nodeParent.parent.left = nodeParent.parent.left.left;
                case RIGHT -> nodeParent.parent.right = nodeParent.parent.right.left;
            }
            return;
        }
        switch (method) {
            case PREDECESSOR -> {
                Node<Integer> predecessorParent = node.left;
                if (predecessorParent.right == null) {
                    node.data = predecessorParent.data;
                    node.left = node.left.left;
                    return;
                }
                while (predecessorParent.right.right != null) {
                    predecessorParent = predecessorParent.right;
                }
                node.data = predecessorParent.right.data;
                predecessorParent.right = null;
            }
            case SUCCESSOR -> {
                Node<Integer> successorParent = node.right;
                if (successorParent.left == null) {
                    node.data = successorParent.data;
                    node.right = node.right.right;
                    return;
                }
                while (successorParent.left.left != null) {
                    successorParent = successorParent.left;
                }
                node.data = successorParent.left.data;
                successorParent.left = null;
            }
        }
    }

    @NotNull
    private NodeParent<Integer> getNodeParent(UUID id) {
        if (id.equals(root.id)) {
            return new NodeParent<>(root, NodeParent.ChildPosition.SELF);
        }
        NodeParent<Integer> result = getNodeParentHelper(root, id);
        if (result == null) {
            throw new IllegalArgumentException("No node found with given id!");
        }
        return result;
    }

    @Nullable
    private NodeParent<Integer> getNodeParentHelper(Node<Integer> root, UUID id) {
        if (root.left != null && id.equals(root.left.id)) {
            return new NodeParent<>(root, NodeParent.ChildPosition.LEFT);
        } else if (root.right != null && id.equals(root.right.id)) {
            return new NodeParent<>(root, NodeParent.ChildPosition.RIGHT);
        }
        if (root.left != null) {
            NodeParent<Integer> nodeParent = getNodeParentHelper(root.left, id);
            if (nodeParent == null && root.right != null) {
                return getNodeParentHelper(root.right, id);
            }
            return nodeParent;
        }
        return null;
    }

    private record NodeParent<T>(Node<T> parent, ChildPosition childPosition) {
        private enum ChildPosition {
            LEFT,
            RIGHT,
            SELF
        }
        private Node<T> getChild() {
            return switch (childPosition) {
                case LEFT -> parent.left;
                case RIGHT -> parent.right;
                case SELF -> parent;
            };
        }
    }

    public enum Deletion {
        PREDECESSOR,
        SUCCESSOR
    }
}
