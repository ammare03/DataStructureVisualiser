package datastructures;

public class Queue {
    private Node first;
    private Node last;
    private int length;

    public boolean isEmpty() {
        return length == 0;
    }

    public class Node {
        public int value;
        public Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public Queue() {
        first = null;
        last = null;
        length = 0;
    }

    public void printQueue() {
        Node temp = first;
        while(temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public Node getFirst() {
        if(first == null) {
            return null;
        } else {
            System.out.println("First: " + first.value);
            return first;
        }
    }

    public void getLast() {
        System.out.println("Last: " + last.value);
    }

    public void getLength() {
        System.out.println("Length: " + length);
    }

    public void enqueue(int value) {
        Node newNode = new Node(value);
        if(length == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        length++;
    }

    public Node dequeue() {
        if(length == 0) {
            return null;
        }

        Node temp = first;
        if(length == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            temp.next = null;
        }
        length--;
        return temp;
    }
}
