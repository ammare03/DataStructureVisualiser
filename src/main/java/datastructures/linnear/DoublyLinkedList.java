package datastructures.linnear;

import exceptions.UnderflowException;

import java.util.UUID;

public class DoublyLinkedList<T> extends LinkedList<T>{
    public DoublyLinkedList(T head) {
        super(head);
    }

    @Override
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
        iterator.next = new Node<>(newId, data, iterator.next, iterator);
        return newId;
    }

    @Override
    public UUID addAfter(T data, UUID id) {
        Node<T> iterator = head;
        while (!iterator.id.equals(id)) {
            iterator = iterator.next;
            if (iterator == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next, iterator);
        return newId;
    }

    @Override
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
        iterator.next = new Node<>(newId, data, iterator.next, iterator);
        return newId;
    }

    @Override
    public UUID prepend(T data) {
        UUID newId = UUID.randomUUID();
        head.previous = new Node<>(newId, data, head, null);
        head = head.previous;
        return newId;
    }

    @Override
    public UUID append(T data) {
        Node<T> iterator = head;
        while (iterator.next != null) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, null, iterator);
        return newId;
    }

    @Override
    public void removeHead() throws UnderflowException {
        if (head.next == null) {
            throw new UnderflowException("Cannot remove the only node!");
        }
        head = head.next;
        head.previous = null;
    }

    @Override
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

    @Override
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
        iterator.next = iterator.next.next;
        if (iterator.next != null) {
            iterator.next.previous = iterator;
        }
    }

    @Override
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
        iterator.next.previous = iterator;
    }
}
