package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CircularQueue<T> implements Iterable<T>, ArrayBased<T> {
    private T[] array;
    private int front = -1;
    private int rear = 0;
    private boolean isWrapped = false;
    private boolean specialCaseQueueEmpty = false;
    public CircularQueue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size cannot be less than one!");
        }
        array = (T[]) new Object[size];
    }
    public void push(T data) throws OverflowException {
        if ((!isWrapped && ((rear == 0) && (front == (array.length - 1)))) || (isWrapped && (front == (rear - 1)))) {
            throw new OverflowException();
        }
        if (front == array.length - 1) {
            array[front = 0] = data;
            isWrapped = !isWrapped;
            specialCaseQueueEmpty = false;
        } else {
            array[++front] = data;
        }
    }
    public T pop() throws UnderflowException {
        if ((isWrapped && (((rear == 0) && (front == (array.length - 1))))) || (!isWrapped && (rear == (front + 1)))) {
            throw new UnderflowException();
        }
        if (rear == array.length - 1) {
            specialCaseQueueEmpty = front == rear;
            isWrapped = !isWrapped;
            rear = 0;
            return array[array.length -1];
        }
        return array[rear++];
    }
    private T[] getNulledArray() {
        T[] nulledArray = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            nulledArray[i] = specialCaseQueueEmpty ? null : (isWrapped ? (((i <= front) || (i >= rear)) ? array[i] : null) : (((i <= front) && (i >= rear)) ? array[i] : null));
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
    public String getIndexState() {
        return "front : " + front + "\n" +
                "rear : " + rear + "\n" +
                "isWrapped : " + isWrapped + "\n" +
                "array : " + array;
    }

    @Override
    public String getIndexStateAfterPop() throws UnderflowException {
        CircularQueue<T> clone = clone();
        clone.pop();
        return clone.getIndexState();
    }

    @Override
    public String getIndexStateAfterPush(T data) throws OverflowException {
        CircularQueue<T> clone = clone();
        clone.push(data);
        return clone.getIndexState();
    }
}
