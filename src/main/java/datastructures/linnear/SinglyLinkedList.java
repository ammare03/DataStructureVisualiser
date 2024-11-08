package datastructures.linnear;

import exceptions.UnderflowException;

import java.util.UUID;

public class SinglyLinkedList<T> extends LinkedList<T> {
    public SinglyLinkedList(T head) {
        super(head);
    }

    public UUID add(T data, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index out of range!");
        }
        if (index == 0) {
            return prepend(data);
        }
        Node<T> iterator = head;
        for (int i = 0; i < index - 1; i++) {
            if (iterator.next == null) {
                throw new IllegalArgumentException("Index out of range!");
            }
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next, null);
        return newId;
    }

    public UUID addAfter(T data, UUID id) {
        Node<T> iterator = head;
        while (!iterator.id.equals(id)) {
            iterator = iterator.next;
            if (iterator == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next, null);
        return newId;
    }

    public UUID addBefore(T data, UUID id) {
        if (head.id.equals(id)) {
            return prepend(data);
        }
        Node<T> iterator = head;
        while (!iterator.next.id.equals(id)) {
            iterator = iterator.next;
            if (iterator.next == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next, null);
        return newId;
    }

    public UUID prepend(T data) {
        UUID newId = UUID.randomUUID();
        head = new Node<>(newId, data, head, null);
        return newId;
    }

    public UUID append(T data) {
        Node<T> iterator = head;
        while (iterator.next != null) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, null, null);
        return newId;
    }

    public void removeHead() throws UnderflowException {
        if (head.next == null) {
            throw new UnderflowException("Cannot remove the only node!");
        }
        head = head.next;
    }

    public void removeTail() throws UnderflowException {
        if (head.next == null) {
            throw new UnderflowException("Cannot remove the only node!");
        }
        Node<T> iterator = head;
        while (iterator.next.next != null) {
            iterator = iterator.next;
        }
        iterator.next = null;
    }

    public void remove(int index) throws UnderflowException {
        if (index < 0) {
            throw new IllegalArgumentException("Index out of range!");
        }
        if (index == 0) {
            removeHead();
            return;
        }
        Node<T> iterator = head;
        for (int i = 0; i < index - 1; i++) {
            if (iterator.next.next == null) {
                throw new IllegalArgumentException("Index out of range!");
            }
            iterator = iterator.next;
        }
        if (iterator.next == null) {
            throw new UnderflowException("Cannot remove the only node!");
        }
        iterator.next = iterator.next.next;
    }

    public void remove(UUID id) throws UnderflowException {
        if (head.id.equals(id)) {
            removeHead();
            return;
        }
        Node<T> iterator = head;
        while (!iterator.next.id.equals(id)) {
            iterator = iterator.next;
            if (iterator.next == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        iterator.next = iterator.next.next;
    }
}
