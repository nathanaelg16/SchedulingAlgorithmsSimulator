public class PrioritySchedulingQueue implements Queue<Task> {
    private static final int MAX_SIZE = 1024;
    private Task[] A;
    private int last;

    public PrioritySchedulingQueue() {
        A = new Task[MAX_SIZE];
        last = -1;
    }

    @Override
    public void enqueue(Task t) {
        A[++last] = t;
        heapifyUpwards(last);
    }

    @Override
    public Task dequeue() {
       Task t = A[0];
       swap(last--, 0);
       heapifyDownwards(0);
       return t;
    }

    @Override
    public Task front() {
        return A[0];
    }

    @Override
    public boolean isEmpty() {
        return last == -2;
    }

    private void heapifyUpwards(int last) {
       if (last == 0) return;
       int parentBurst = A[parentOf(last)].getBurstTime();
       int lastBurst = A[last].getBurstTime();
       if (lastBurst < parentBurst) {
           swap(last, parentOf(last));
           heapifyUpwards(parentOf(last));
       }
    }

    private void swap(int last, int parent) {
        Task temp = A[last];
        A[last] = A[parent];
        A[parent] = temp;
    }

    private int parentOf(int i) {
        return (i - 1) / 2;
    }

    public boolean isFull() {
        return last == MAX_SIZE - 1;
    }
}
