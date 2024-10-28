package datastructures;

public class LinkedList {
    public Node head;

    public class Node {
        public int value;
        public Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public LinkedList() {
        head = null;
    }

    public void printList() {
        Node temp = head;
        while(temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void append(int value) {
        Node newNode = new Node(value);

        if(head == null) {
            head = newNode;
        } else {
            Node tail = getTail();
            tail.next = newNode;
        }
    }

    public Node removeLast() {
        if(head == null) return null;

        Node temp = head;
        Node pre = head;
        if(getLength() == 1) {
            head = null;
        } else {
            while(temp.next != null) {
                pre = temp;
                temp = temp.next;
            }
            pre.next = null;
        }

        return temp;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
    }

    public Node removeFirst() {
        if(head == null) return null;

        Node temp = head;
        head = head.next;
        temp.next = null;
        return temp;
    }

    public Node findNode(int value) {
        Node temp = head;
        while(temp != null) {
            if(temp.value == value) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean setNodeValue(int targetValue, int newValue) {
        Node targetNode = findNode(targetValue);
        if (targetNode != null) {
            targetNode.value = newValue;  // Update the node's value
            return true;
        }
        return false; // Return false if the target node is not found
    }

    public boolean insertAfter(int targetValue, int value) {
        Node targetNode = findNode(targetValue);
        if(targetNode == null) return false;

        Node newNode = new Node(value);
        newNode.next = targetNode.next;
        targetNode.next = newNode;
        return true;
    }

    public Node removeByValue(int value) {
        if(head == null) return null;

        if(head.value == value) {
            return removeFirst();
        }

        Node temp = head;
        while(temp.next != null && temp.next.value != value) {
            temp = temp.next;
        }

        if(temp.next == null) {
            return null;
        }

        Node nodeToRemove = temp.next;
        temp.next = nodeToRemove.next;
        nodeToRemove.next = null;

        return nodeToRemove;
    }

    public Node getTail() {
        if(head == null) return null;

        Node temp = head;
        while(temp.next != null) {
            temp = temp.next;
        }
        return temp;
    }

    public int getLength() {
        if(head == null) return 0;

        int count = 0;
        Node temp = head;

        while(temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}
