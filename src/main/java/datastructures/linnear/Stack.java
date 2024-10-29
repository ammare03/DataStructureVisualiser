package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Stack<T> implements Iterable<T> {
    private int top = -1;
    private T[] array;
    public Stack(int size) {
        ArrayList<T> temp = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            temp.add((T) new Object());
        }
        array = (T[]) temp.toArray();
    }
    public void push(T data) throws OverflowException {
        if (top == array.length) {
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

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(Arrays.copyOfRange(array, 0, top + 1)).iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Arrays.stream(Arrays.copyOfRange(array, 0, top + 1)).forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Arrays.stream(Arrays.copyOfRange(array, 0, top + 1)).spliterator();
    }
}