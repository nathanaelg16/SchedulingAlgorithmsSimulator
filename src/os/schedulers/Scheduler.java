// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.Queue;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An archetype for all implementations
 * of scheduling algorithms.
 */
public abstract class Scheduler implements Algorithm {
    /**
     * List of Tasks to be queued, scheduled, and executed.
     */
    private final ArrayList<Task> taskList;
    /**
     * CPU simulator to be used.
     */
    protected CPU cpu;
    /**
     * Queue used for this scheduler.
     */
    protected Queue<Task> queue;
    /**
     * Keeps track of the tasks yet to be queued.
     */
    private int taskListIterator;

    /**
     * Total running time of the simulation.
     */
    private int time;

    public Scheduler(CPU cpu, ArrayList<Task> taskList, Queue<Task> queue) {
        this.cpu = cpu;
        this.queue = queue;
        this.taskList = taskList;
        this.taskList.sort(Comparator.comparingInt(Task::getArrivalTime)); // sort by arrival time, necessary for determining when to insert into queues
        this.taskListIterator = 0;
        this.time = 0;
    }

    /**
     * Inserts a task into the queue
     *
     * @param t task to be inserted into the queue
     */
    protected void queue(Task t) {
        queue.enqueue(t);
    }

    /**
     * Returns the next task to be scheduled. This task
     * will be the task at the front of the queue.
     *
     * @return the next task to be scheduled
     */
    @Override
    public Task pickNextTask() {
        return queue.dequeue();
    }

    /**
     * Runs the scheduler.
     * <p>
     * The functionality of this method is
     * dependent on the scheduling algorithm
     * being used, therefore it is left unimplemented.
     */
    public abstract void run();

    /**
     * Returns true if the scheduler has finished queueing and
     * scheduling all assigned tasks.
     *
     * @return true if the scheduler has finished queueing and scheduling
     * all assigned tasks.
     */
    public boolean shouldExit() {
        return queue.isEmpty() && taskListIterator >= taskList.size();
    }

    /**
     * Increments the time of the simulation and invokes the scheduler.
     * If any tasks have "arrived", these tasks are queued
     * up to be scheduled.
     */
    public void tick() {
        Task t;
        // queue up any new tasks that have arrived
        while (taskListIterator < taskList.size() && (t = taskList.get(taskListIterator)).getArrivalTime() == this.time) {
            this.queue(t);
            this.taskListIterator++;
        }
        this.run(); // run the scheduler
        this.time++; // increment the time of the simulation
    }
}
