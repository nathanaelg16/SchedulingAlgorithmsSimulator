package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.SchedulingQueue;

import java.util.ArrayList;

/**
 * A scheduler that implements the First-Come, First-Served (FCFS)
 * (non-preemptive) scheduling algorithm.
 * <p>
 * This scheduler uses a linked-list based queue to determine the
 * next task to run.
 */
public class FirstComeFirstServedScheduler extends Scheduler {

    public FirstComeFirstServedScheduler(CPU cpu, ArrayList<Task> taskList) {
        super(cpu, taskList, new SchedulingQueue());
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
