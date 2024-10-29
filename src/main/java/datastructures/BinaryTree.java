package datastructures;

public class BinaryTree {
    public Node root;

    public class Node {
        public int value;
        public Node left;
        public Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BinaryTree() {
        root = null;
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    public String inorder() {
        StringBuilder result = new StringBuilder();
        inorderRec(root, result);
        return result.toString().trim();
    }

    private void inorderRec(Node root, StringBuilder result) {
        if (root != null) {
            inorderRec(root.left, result);
            result.append(root.value).append(" ");
            inorderRec(root.right, result);
        }
    }

    public String preorder() {
        StringBuilder result = new StringBuilder();
        preorderRec(root, result);
        return result.toString().trim();
    }

    private void preorderRec(Node root, StringBuilder result) {
        if (root != null) {
            result.append(root.value).append(" ");
            preorderRec(root.left, result);
            preorderRec(root.right, result);
        }
    }

    public String postorder() {
        StringBuilder result = new StringBuilder();
        postorderRec(root, result);
        return result.toString().trim();
    }

    private void postorderRec(Node root, StringBuilder result) {
        if (root != null) {
            postorderRec(root.left, result);
            postorderRec(root.right, result);
            result.append(root.value).append(" ");
        }
    }
}
