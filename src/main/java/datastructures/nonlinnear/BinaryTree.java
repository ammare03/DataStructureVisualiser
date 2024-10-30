package datastructures.nonlinnear;

import exceptions.UnderflowException;

import java.util.List;
import java.util.UUID;

public class BinaryTree<T> extends BaseTree<T> {

    public BinaryTree(T root) {
        super(root);
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

    public void remove(UUID id) throws UnderflowException {
        if(root.id.equals(id)) {
            throw new UnderflowException("Cannot remove the root!");
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

    public static <T> BinaryTree<T> constructBinaryTree(T[] inOrder, T[] preOrder) {
        if (inOrder.length != preOrder.length) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }
        if (inOrder.length < 1) {
            return null;
        }

        T rootData = preOrder[0];
        BinaryTree<T> tree = new BinaryTree<>(rootData);

        int inRootIndex = -1;
        for (int i = 0; i < inOrder.length; i++) {
            T inData = inOrder[i];
            if (inData.equals(rootData)) {
                inRootIndex = i;
                break;
            }
        }

        T[] inSubLeft = (T[]) List.of(inOrder).subList(0, inRootIndex).toArray();
        T[] preSubRLeft = (T[]) List.of(preOrder).subList(1, inSubLeft.length + 1).toArray();
        T[] inSubRight = (T[]) List.of(inOrder).subList(inRootIndex + 1, inOrder.length).toArray();
        T[] preSubRight = (T[]) List.of(preOrder).subList(inSubLeft.length + 1, preOrder.length).toArray();

        BinaryTree<T> leftSubTree = constructBinaryTree(inSubLeft, preSubRLeft);
        BinaryTree<T> rightSubTree = constructBinaryTree(inSubRight, preSubRight);

        tree.root.left = leftSubTree == null ? null : leftSubTree.root;
        tree.root.right = rightSubTree == null ? null : rightSubTree.root;

        return tree;
    }

}
