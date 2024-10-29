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

    private T[] getNulledArray() {
        ArrayList<T> temp = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            temp.add((T) new Object());
        }
        T[] nulledArray = (T[]) temp.toArray();
        for (int i = 0; i < array.length; i++) {
            if (i <= top) {
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