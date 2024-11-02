package datastructures.linnear;

import datastructures.StateFull;
import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Queue<T> implements Iterable<T>, StateFull {
    protected T[] array;
    protected int front = -1;
    protected int rear = 0;
    public Queue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size cannot be less than one!");
        }
        array = (T[]) new Object[size];
    }
    public void enqueue(T data) throws OverflowException {
        if (front == array.length - 1) {
            throw new OverflowException("Queue Overflow!");
        }
        array[++front] = data;
    }
    public T dequeue() throws UnderflowException {
        if (front < rear) {
            throw new UnderflowException("Queue Underflow!");
        }
        return array[rear++];
    }
    public T peek() throws UnderflowException {
        if (front < rear) {
            throw new UnderflowException();
        }
        return array[rear];
    }

    protected T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            nulledArray[i] = i >= rear && i <= front ? array[i] : null;
        }
        return nulledArray;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(getNulledArray()).iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Arrays.asList(getNulledArray()).forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Arrays.asList(getNulledArray()).spliterator();
    }

    @Override
    protected Queue<T> clone() {
        Queue<T> queue = new Queue<>(array.length);
        queue.array = array.clone();
        queue.front = front;
        queue.rear = rear;
        return queue;
    }

    public Map<String, String> getStateAfterDequeue() throws UnderflowException {
        Queue<T> clone = clone();
        clone.dequeue();
        return clone.getState();
    }

    public Map<String, String> getStateAfterEnqueue(T data) throws OverflowException {
        Queue<T> clone = clone();
        clone.enqueue(data);
        return clone.getState();
    }

    @Override
    public Map<String, String> getState() {
        return Map.of(
                "front", String.valueOf(front),
                "rear", String.valueOf(rear),
                "array", Arrays.toString(array)
        );
    }
}
