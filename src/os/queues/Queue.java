// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.queues;

/**
 * Queue ADT interface.
 *
 * @param <T> The type of elements in the queue
 */
public interface Queue<T> {
    /**
     * Adds an element to the back of the queue
     *
     * @param t element to be added to the queue
     */
    void enqueue(T t);

    /**
     * Removes an element from the front of the queue
     *
     * @return the element removed
     */
    T dequeue();

    /**
     * Returns the element at the front of the queue
     *
     * @return the element at the front of the queue
     */
    T front();

    /**
     * Returns true if the queue contains no elements
     *
     * @return true if the queue contains no elements
     */
    boolean isEmpty();

    /**
     * Returns true if the queue has exceeded the maximum number of elements it may carry
     *
     * @return true if the queue has exceeded the maximum number of elements it may carry
     */
    boolean isFull();
}