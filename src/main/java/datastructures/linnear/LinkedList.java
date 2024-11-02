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

public abstract class LinkedList<T> implements Iterable<Node<T>>, StateFull {
    Node<T> head;

    public LinkedList(T head) {
        this.head = new Node<>(UUID.randomUUID(), head, null, null);
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

    public abstract UUID add(T data, int index);

    public abstract UUID addAfter(T data, UUID id);

    public abstract UUID addBefore(T data, UUID id);

    public abstract UUID prepend(T data);

    public abstract UUID append(T data);

    public abstract void removeHead() throws UnderflowException;

    public abstract void removeTail() throws UnderflowException;

    public abstract void remove(int index) throws UnderflowException;

    public abstract void remove(UUID id) throws UnderflowException;

    @NotNull
    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            Node<T> iterator = new Node<>(null, null, head, null);

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
        protected final UUID id;
        private final T data;
        protected Node<T> next;
        protected Node<T> previous;

        protected Node(UUID id, T data, Node<T> next, Node<T> previous) {
            this.id = id;
            this.data = data;
            this.next = next;
            this.previous = previous;
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
                    "next", next == null ? "null" : String.valueOf(next.hashCode()),
                    "previous", previous == null ? "null" : String.valueOf(previous.hashCode())
            );
        }
    }
}
