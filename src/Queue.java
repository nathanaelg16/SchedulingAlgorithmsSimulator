public interface Queue<T> {
    public void enqueue(T t);
    public T dequeue();
    public T front();
    public boolean isEmpty();
}
