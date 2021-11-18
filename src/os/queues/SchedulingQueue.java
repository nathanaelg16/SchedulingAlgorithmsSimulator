// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.queues;

import os.Task;

/**
 * Implements the Queue ADT using a linked list.
 */
public class SchedulingQueue implements Queue<Task> {
    /**
     * Pointer to the first node in the queue
     */
    private TaskNode root;

    /**
     * Pointer to the last node in the queue
     */
    private TaskNode last;

    /**
     * Adds a task to the back of the queue in O(1) time.
     *
     * @param task task to be added to the queue
     */
    @Override
    public void enqueue(Task task) {
        if (this.isEmpty()) {
            root = new TaskNode(task);
            last = root;
        } else {
            last.next = new TaskNode(task);
            last = last.next;
        }
    }

    /**
     * Removes a task from the front of the queue in O(1) time.
     *
     * @return the task removed from the queue
     */
    @Override
    public Task dequeue() {
        if (this.isEmpty()) return null;
        Task task = root.getTask();
        root = root.next;
        return task;
    }

    /**
     * Returns the task at the front of the queue
     *
     * @return the task at the front of the queue
     */
    @Override
    public Task front() {
        if (this.isEmpty()) return null;
        return root.getTask();
    }

    /**
     * Returns true if there are no tasks in the queue
     *
     * @return true if there are no tasks in the queue
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns whether the queue is full. This value is always false
     * since a linked-list based queue may carry as many elements as
     * can be allocated in memory.
     *
     * @return whether the queue is full (always false)
     */
    @Override
    public boolean isFull() {
        return false;
    }

    /**
     * Wrapper node containing the task
     * in the queue and a pointer to
     * the next element in the queue
     */
    class TaskNode {
        /**
         * The Task inserted into the queue
         */
        private final Task task;

        /**
         * A pointer to the next node in the queue
         */
        public TaskNode next;

        public TaskNode(Task task) {
            this.task = task;
            this.next = null;
        }

        public Task getTask() {
            return task;
        }
    }
}