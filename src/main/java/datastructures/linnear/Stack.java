package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Stack<T> implements Iterable<T> {
    private int top = -1;
    private T[] array;
    public Stack(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size cannot be less than one!");
        }
        array = (T[]) new Object[size];
    }
    public void push(T data) throws OverflowException {
        if (top == array.length - 1) {
            throw new OverflowException();
        }
        array[++top] = data;
    }
    public T pop() throws UnderflowException {
        if(top == -1) {
            throw new UnderflowException();
        }
        return array[top--];
    }
    public T peek() throws UnderflowException {
        if(top == -1) {
            throw new UnderflowException();
        }
        return array[top];
    }

    private T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            nulledArray[i] = i <= top ? array[i] : null;
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