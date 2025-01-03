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

public class Stack<T> implements Iterable<T>, StateFull {
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
            throw new OverflowException("Stack Overflow!");
        }
        array[++top] = data;
    }
    public T pop() throws UnderflowException {
        if(top == -1) {
            throw new UnderflowException("Stack Underflow!");
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
    protected Stack<T> clone() {
        Stack<T> stack = new Stack<>(array.length);
        stack.array = array.clone();
        stack.top = top;
        return stack;
    }

    public Map<String, String> getStateAfterPop() throws UnderflowException {
        Stack<T> clone = clone();
        clone.pop();
        return clone.getState();
    }

    public Map<String, String> getStateAfterPush(T data) throws OverflowException {
        Stack<T> clone = clone();
        clone.push(data);
        return clone.getState();
    }

    @Override
    public Map<String, String> getState() {
        return Map.of(
                "top", String.valueOf(top),
                "array", Arrays.toString(array)
        );
    }
}