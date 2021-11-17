package os.queues;

import os.Task;

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
        //if (last < 0) return null;
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
        return last == -1;
    }

    private void heapifyUpwards(int i) {
       if (i == 0) return;
       int parentBurst = A[parentOf(i)].getBurstTime();
       int iBurst = A[i].getBurstTime();
       if (iBurst < parentBurst) {
           swap(i, parentOf(i));
           heapifyUpwards(parentOf(i));
       }
    }

    private void heapifyDownwards(int i) {
        if (i > last) return;
        int iBurst = A[i].getBurstTime();
        int left = leftOf(i);
        if (left <= last && A[left] != null) {
            int leftBurst = A[left].getBurstTime();
            if (leftBurst < iBurst) {
                swap(left, i);
                heapifyDownwards(left);
            }
        }
        iBurst = A[i].getBurstTime();
        int right = left + 1;
        if (right <= last && A[right] != null) {
           int rightBurst = A[right].getBurstTime();
           if (rightBurst < iBurst) {
               swap(right, i);
               heapifyDownwards(right);
           }
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

    private int leftOf(int i) {
        return 2 * i + 1;
    }

    public boolean isFull() {
        return last == MAX_SIZE - 1;
    }
}
