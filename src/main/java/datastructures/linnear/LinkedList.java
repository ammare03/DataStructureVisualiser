package datastructures.linnear;

import datastructures.StateFull;
import datastructures.linnear.LinkedList.Node;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;

public class LinkedList<T> implements Iterable<Node<T>>, StateFull {
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
            if (iterator == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        return iterator;
    }

    public UUID add(T data, int index) {
        if (index == 0) {
            return prepend(data);
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

    public UUID addAfter(T data, UUID id) {
        Node<T> iterator = head;
        while (!iterator.id.equals(id)) {
            iterator = iterator.next;
            if (iterator == null) {
                throw new IllegalArgumentException("No node found with the given id!");
            }
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, iterator.next);
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
        iterator.next = new Node<>(newId, data, iterator.next);
        return newId;
    }

    public UUID prepend(T data) {
        UUID newId = UUID.randomUUID();
        head = new Node<>(newId, data, head);
        return newId;
    }

    public UUID append(T data) {
        Node<T> iterator = head;
        while (iterator.next != null) {
            iterator = iterator.next;
        }
        UUID newId = UUID.randomUUID();
        iterator.next = new Node<>(newId, data, null);
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
        if (index == 0) {
            removeHead();
            return;
        }
        Node<T> iterator = head;
        for (int i = 0; i < index - 1; i++) {
            if (iterator.next == null) {
                throw new IllegalArgumentException("Index out of range");
            }
            iterator = iterator.next;
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

    @Override
    public Map<String, String> getState() {
        return Map.of("head", String.valueOf(head.hashCode()));
    }

    public static class Node<T> implements StateFull {
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
        public Map<String, String> getState() {
            return Map.of(
                    "data", data.toString(),
                    "next", next == null ? "null" : String.valueOf(next.hashCode())
            );
        }
    }
}
