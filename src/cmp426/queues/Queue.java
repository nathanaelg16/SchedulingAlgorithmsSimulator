package cmp426.queues;

public interface Queue<T> {
    void enqueue(T t);
    T dequeue();
    T front();
    boolean isEmpty();
}
