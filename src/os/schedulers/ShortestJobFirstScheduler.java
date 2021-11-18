// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.PrioritySchedulingQueue;

import java.util.ArrayList;

/**
 * A scheduler that implements the Shortest Job First (SJF)
 * (non-preemptive) scheduling algorithm.
 * <p>
 * This scheduler uses a Heap-based Priority Queue
 * to determine the next task to run. The Task
 * at the front of the queue will always be the
 * shortest task in the queue.
 */
public class ShortestJobFirstScheduler extends Scheduler {

    public ShortestJobFirstScheduler(CPU cpu, ArrayList<Task> taskList) {
        super(cpu, taskList, new PrioritySchedulingQueue());
    }

    /**
     * Schedules the next task to run.
     */
    @Override
    public void schedule() {
        this.cpu.run(this.pickNextTask());
    }

    /**
     * Runs the scheduler.
     * <p>
     * If the CPU is idle, then the next task at the
     * front of the queue is scheduled to run.
     */
    @Override
    public void run() {
        if (this.cpu.isIdle()) this.schedule();
    }
}
