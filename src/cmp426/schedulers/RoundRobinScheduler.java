package cmp426.schedulers;

import cmp426.CPU;
import cmp426.Task;
import cmp426.queues.SchedulingQueue;

import java.util.ArrayList;

public class RoundRobinScheduler extends Scheduler {
    private final int timeQuantum;

    public RoundRobinScheduler(CPU cpu, ArrayList<Task> taskList, int timeQuantum) {
        super(cpu, taskList, new SchedulingQueue());
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void schedule() {
        Task currentTask = this.cpu.getRunningTask();

        if (currentTask != null) {
            if (currentTask.getBurstTimeRemaining() > 0) this.queue(currentTask);
        }

        Task nextTask = this.pickNextTask();
        cpu.run(nextTask);
    }

    @Override
    public void run() {
        if (cpu.isIdle() || cpu.elapsedTime() % this.timeQuantum == 0) this.schedule();
    }
}
