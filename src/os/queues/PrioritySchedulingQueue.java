// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.queues;

import os.Task;

/**
 * Implements a heap-based priority queue using the Queue ADT.
 * This priority queue uses a min-heap to guarantee that
 * the Task at the front of the queue is always
 * the Task with the shortest burst time.
 */
public class PrioritySchedulingQueue implements Queue<Task> {
    /**
     * Max number of Tasks in the queue, chosen rather arbitrarily.
     */
    private static final int MAX_SIZE = 1024;

    /**
     * Array containing the tasks in the queue.
     */
    private final Task[] A;

    /**
     * Index of the last Task inserted in the queue.
     */
    private int last;

    public PrioritySchedulingQueue() {
        A = new Task[MAX_SIZE];
        last = -1;
    }

    /**
     * Adds a task to the back of the queue in O(log n) time
     *
     * @param t task to be added to the queue
     */
    @Override
    public void enqueue(Task t) {
        A[++last] = t;
        heapifyUpwards(last);
    }

    /**
     * Removes the task at the front of the queue in O(log n) time
     *
     * @return the task at the front of the queue
     */
    @Override
    public Task dequeue() {
        if (last < 0) return null;
        Task t = A[0];
        swap(last--, 0);
        heapifyDownwards(0);
        return t;
    }

    /**
     * Returns the task at the front of the queue
     *
     * @return the task at the front of the queue
     */
    @Override
    public Task front() {
        return A[0];
    }

    /**
     * Returns true if there are no tasks in the queue
     *
     * @return true if there are no tasks in the queue
     */
    @Override
    public boolean isEmpty() {
        return last == -1;
    }

    /**
     * Recursively maintains the heap invariant by checking that
     * the burst time of a task is not less than that of its
     * parent. If this is true, the task is swapped with its
     * parent, and we continue by recursing with the parent
     * until we either reach the root node or the heap invariant
     * is maintained.
     * <p>
     * Runs in O(log n) in the worst case.
     *
     * @param i the index of the task being checked
     */
    private void heapifyUpwards(int i) {
        if (i == 0) return;
        int parentBurst = A[parentOf(i)].getBurstTime();
        int iBurst = A[i].getBurstTime();
        if (iBurst < parentBurst) {
            swap(i, parentOf(i));
            heapifyUpwards(parentOf(i));
        }
    }

    /**
     * Recursively maintains the heap invariant by checking that
     * the burst time of a task is less than the burst time of
     * its children. If this is false, the task is swapped with
     * its child, and we continue by recursing with the child
     * until we reach the end of the queue or the heap invariant
     * is maintained.
     * <p>
     * Runs in O(log n) in the worst case.
     *
     * @param i the index of the task being checked
     */
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

    /**
     * Swaps the position of two tasks with each other.
     *
     * @param t1 the index of the first task
     * @param t2 the index of the second task
     */
    private void swap(int t1, int t2) {
        Task temp = A[t1];
        A[t1] = A[t2];
        A[t2] = temp;
    }

    /**
     * Returns the index of the parent of i
     *
     * @param i the index whose parent we would like to find
     * @return the index of the parent of i
     */
    private int parentOf(int i) {
        return (i - 1) / 2;
    }

    /**
     * Returns the index of the left child of i
     *
     * @param i the index whose left child we would like to find
     * @return the index of the left child of i
     */
    private int leftOf(int i) {
        return 2 * i + 1;
    }

    /**
     * Returns true if the queue has exceeded the maximum number of Tasks it may contain
     *
     * @return true if the queue has exceeded the maximum number of Tasks it may contain
     */
    public boolean isFull() {
        return last == MAX_SIZE - 1;
    }
}