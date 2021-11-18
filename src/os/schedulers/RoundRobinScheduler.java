// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.SchedulingQueue;

import java.util.ArrayList;

/**
 * A scheduler that implements the Round-Robin (RR)
 * (preemptive) scheduling algorithm.
 * <p>
 * This scheduler uses a linked list-based queue
 * to determine the next task to run.
 */
public class RoundRobinScheduler extends Scheduler {
    /**
     * The amount of time before the scheduler preempts a running task.
     * Also called a time slice.
     */
    private final int timeQuantum;

    public RoundRobinScheduler(CPU cpu, ArrayList<Task> taskList, int timeQuantum) {
        super(cpu, taskList, new SchedulingQueue());
        this.timeQuantum = timeQuantum;
    }

    /**
     * Schedules the next task to run. If the currently running task
     * has not finished executing, then this task is added back into
     * the queue to be run later.
     */
    @Override
    public void schedule() {
        Task currentTask = this.cpu.getRunningTask();

        if (currentTask != null) {
            if (currentTask.getBurstTimeRemaining() > 0) this.queue(currentTask);
        }

        cpu.run(this.pickNextTask());
    }

    /**
     * Runs the scheduler.
     * <p>
     * If the CPU is idle, or if the time quantum has been met,
     * the next task at the front of the queue is scheduled to run.
     */
    @Override
    public void run() {
        if (cpu.isIdle() || cpu.elapsedTime() % this.timeQuantum == 0) this.schedule();
    }
}
