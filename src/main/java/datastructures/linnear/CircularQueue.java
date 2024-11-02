package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;

import java.util.*;

public class CircularQueue<T> extends Queue<T> {
    private boolean isWrapped = false;
    private boolean specialCaseQueueEmpty = false;
    public CircularQueue(int size) {
        super(size);
    }

    @Override
    public void enqueue(T data) throws OverflowException {
        if ((!isWrapped && ((rear == 0) && (front == (array.length - 1)))) || (isWrapped && (front == (rear - 1)))) {
            throw new OverflowException("Circular Queue Overflow!");
        }
        if (front == array.length - 1) {
            array[front = 0] = data;
            isWrapped = !isWrapped;
            specialCaseQueueEmpty = false;
        } else {
            array[++front] = data;
        }
    }

    @Override
    public T dequeue() throws UnderflowException {
        if ((isWrapped && (((rear == 0) && (front == (array.length - 1))))) || (!isWrapped && (rear == (front + 1)))) {
            throw new UnderflowException("Circular Queue Underflow!");
        }
        if (rear == array.length - 1) {
            specialCaseQueueEmpty = front == rear;
            isWrapped = !isWrapped;
            rear = 0;
            return array[array.length -1];
        }
        return array[rear++];
    }

    @Override
    protected T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            nulledArray[i] = specialCaseQueueEmpty ? null : (isWrapped ? (((i <= front) || (i >= rear)) ? array[i] : null) : (((i <= front) && (i >= rear)) ? array[i] : null));
        }
        return nulledArray;
    }

    @Override
    protected CircularQueue<T> clone() {
        CircularQueue<T> circularQueue = new CircularQueue<>(array.length);
        circularQueue.array = array.clone();
        circularQueue.front = front;
        circularQueue.rear = rear;
        circularQueue.isWrapped = isWrapped;
        circularQueue.specialCaseQueueEmpty = specialCaseQueueEmpty;
        return circularQueue;
    }

    @Override
    public Map<String, String> getState() {
        Map<String, String> state = new HashMap<>(super.getState());
        state.put("isWrapped", String.valueOf(isWrapped));
        return state;
    }
}
