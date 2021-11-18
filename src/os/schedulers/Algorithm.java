// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.schedulers;

import os.Task;

/**
 * Scheduling Algorithm interface as provided
 * by the project specifications. All scheduling
 * algorithms must implement this interface.
 */
public interface Algorithm {

    /**
     * Per the project specifications:
     * <p>
     * This method is the implementation of the scheduling algorithm.
     * This method obtains the next task to be run on the CPU by invoking
     * the pickNextTask() method and then executes this Task by calling the
     * run() method in the CPU class
     */
    void schedule();

    /**
     * Selects the next task to be scheduled
     *
     * @return the next task to be scheduled
     */
    Task pickNextTask();
}