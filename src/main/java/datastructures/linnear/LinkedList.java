package datastructures.linnear;

import datastructures.linnear.LinkedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;

public class LinkedList<T> implements Iterable<Node<T>> {
    Node<T> head;

    public LinkedList(T head) {
        this.head = new Node<>(UUID.randomUUID(), head, null);
    }

    public Node<T> get(int index) {
        Node<T> iterator = head;
        for (int i = 0; i < index; i++) {
            if (iterator.next == null) {
                throw new IllegalArgumentException("Index out of range");
            }
            iterator = iterator.next;
        }
        return iterator;
    }

    public Node<T> get(UUID id) {
        Node<T> iterator = head;
        while (!iterator.id.equals(id)) {
            iterator = iterator.next;
        }
        return iterator;
    }

    public UUID add(T data, int index) {
        if (index == 0) {
            return addFirst(data);
        }
        Node<T> iterator = head;
        for (int i = 0; i < index - 1; i++) {
            if (iterator.next == null) {
                throw new IllegalArgumentException("Index out of range");
            }
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next);
        return newId;
    }

    public UUID addNext(T data, UUID id) {
        Node<T> iterator = head;
        while (!iterator.id.equals(id)) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next);
        return newId;
    }

    public UUID addPrevious(T data, UUID id) {
        if (head.id.equals(id)) {
            return addFirst(data);
        }
        Node<T> iterator = head;
        while (!iterator.next.id.equals(id)) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next);
        return newId;
    }

    public UUID addFirst(T data) {
        UUID newId = UUID.randomUUID();
        head = new Node<>(newId, data, head);
        return newId;
    }

    public UUID addLast(T data) {
        Node<T> iterator = head;
        while (iterator.next != null) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, null);
        return newId;
    }

    @NotNull
    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            Node<T> iterator = new Node<>(null, null, head);

            @Override
            public boolean hasNext() {
                return iterator.next != null;
            }

            @Override
            public Node<T> next() {
                iterator = iterator.next;
                return iterator;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Node<T>> action) {
        iterator().forEachRemaining(action);
    }

    @Override
    public Spliterator<Node<T>> spliterator() {
        return Iterable.super.spliterator();
    }

    public static class Node<T> {
        private final UUID id;
        private final T data;
        private Node<T> next;

        private Node(UUID id, T data, Node<T> next) {
            this.id = id;
            this.data = data;
            this.next = next;
        }

        public UUID getId() {
            return id;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
