package datastructures.linnear;

import exceptions.UnderflowException;

import java.util.UUID;

public class CircularSinglyLinkedList<T> extends SinglyLinkedList<T> {
    public CircularSinglyLinkedList(T head) {
        super(head);
        this.head.next = this.head;
    }

    @Override
    public UUID append(T data) {
        Node<T> iterator = head;
        while (iterator.next != head) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, head, null);
        return newId;
    }

    @Override
    public void removeTail() throws UnderflowException {
        if (head.next == null) {
            throw new UnderflowException("Cannot remove the only node!");
        }
        Node<T> iterator = head;
        while (iterator.next.next != head) {
            iterator = iterator.next;
        }
        iterator.next = head;
    }
}
