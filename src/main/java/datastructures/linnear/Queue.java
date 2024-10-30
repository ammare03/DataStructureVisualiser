package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Queue<T> implements Iterable<T>, ArrayBased<T> {
    private T[] array;
    private int front = -1;
    private int rear = 0;
    public Queue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size cannot be less than one!");
        }
        array = (T[]) new Object[size];
    }
    public void push(T data) throws OverflowException {
        if (front == array.length - 1) {
            throw new OverflowException();
        }
        array[++front] = data;
    }
    public T pop() throws UnderflowException {
        if (front < rear) {
            throw new UnderflowException();
        }
        return array[rear++];
    }
    public T peek() throws UnderflowException {
        if (front < rear) {
            throw new UnderflowException();
        }
        return array[rear];
    }

    private T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            nulledArray[i] = i >= rear && i <= front ? array[i] : null;
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

    @Override
    protected Queue<T> clone() {
        Queue<T> queue = new Queue<>(array.length);
        queue.array = array.clone();
        queue.front = front;
        queue.rear = rear;
        return queue;
    }

    @Override
    public String getIndexState() {
        return "front : " + front + "\n" +
                "rear : " + rear + "\n" +
                "array : " + Arrays.toString(array);
    }

    @Override
    public String getIndexStateAfterPop() throws UnderflowException {
        Queue<T> clone = clone();
        clone.pop();
        return clone.getIndexState();
    }

    @Override
    public String getIndexStateAfterPush(T data) throws OverflowException {
        Queue<T> clone = clone();
        clone.push(data);
        return clone.getIndexState();
    }
}
