package tqs.lab1;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private List<T> collection;
    private int max;

    public TqsStack() {
        collection = new LinkedList<T>();
    }

    public TqsStack(int max) {
        this();
        this.max = max;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
        return collection.remove(0);
    }

    public int size() {
        return collection.size();
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
        return collection.get(0);
    }

    public void push(T item) {
        if (max > 0 && size() == max) {
            throw new IllegalStateException("Stack is full.");
        }
        collection.add(0, item);
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
