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
 * A scheduler that implements the Shortest Remaining Time
 * First (SRTF, also known as preemptive SJF) scheduling algorithm.
 * <p>
 * This scheduler uses a Heap-based Priority Queue
 * to determine the next task to run. The Task
 * at the front of the queue will always be the
 * shortest task in the queue.
 */
public class ShortestRemainingTimeFirst extends Scheduler {

    public ShortestRemainingTimeFirst(CPU cpu, ArrayList<Task> taskList) {
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
     * If a shorter task exists in the queue,
     * then the running task is preempted,
     * added back into the queue, and the
     * shorter task is scheduled to run.
     */
    @Override
    public void run() {
        if (this.cpu.isIdle()) this.schedule();
        if (this.queue.front().getBurstTimeRemaining() < this.cpu.getRunningTask().getBurstTimeRemaining()) {
            this.queue(this.cpu.getRunningTask());
            this.schedule();
        }
    }
}
