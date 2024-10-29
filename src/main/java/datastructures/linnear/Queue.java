package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Queue<T> implements Iterable<T> {
    private T[] array;
    private int front = -1;
    private int rear = -1;
    public Queue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size cannot be less than one!");
        }
        array = (T[]) new Object[size];
    }
    public void push(T t) throws OverflowException {
        if (front == array.length - 1) {
            throw new OverflowException();
        }
        array[++front] = t;
        if (rear == -1) {
            rear++;
        }
    }
    public T pop() throws UnderflowException {
        if (front < rear || rear == -1) {
            throw new UnderflowException();
        }
        return array[rear++];
    }
    public T peek() throws UnderflowException {
        if (front < rear || rear == -1) {
            throw new UnderflowException();
        }
        return array[rear];
    }

    private T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            if (i >= rear && i <= front) {
                nulledArray[i] = array[i];
            } else {
                nulledArray[i] = null;
            }
        }
        return nulledArray;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(getNulledArray()).iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Arrays.stream(getNulledArray()).forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Arrays.stream(getNulledArray()).spliterator();
    }
}
