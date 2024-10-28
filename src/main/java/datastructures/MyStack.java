package datastructures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;

import java.util.Stack;

public class MyStack<T> extends Stack<T> {
    int size;
    public MyStack(int size) {
        this.size = size;
    }

    @Override
    public T push(T item) {
        if (size == size()) {
            throw new StackOverflowException();
        }
        return super.push(item);
    }

    @Override
    public synchronized T pop() {
        if (size() == 0) {
            throw new StackUnderflowException();
        }
        return super.pop();
    }
}
