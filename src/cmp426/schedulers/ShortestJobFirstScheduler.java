package cmp426.schedulers;

import cmp426.CPU;
import cmp426.Task;
import cmp426.queues.PrioritySchedulingQueue;

import java.util.ArrayList;

public class ShortestJobFirstScheduler extends Scheduler {

    public ShortestJobFirstScheduler(CPU cpu, ArrayList<Task> taskList) {
        super(cpu, taskList, new PrioritySchedulingQueue());
    }

    @Override
    public void schedule() {
        this.cpu.run(this.pickNextTask());
    }

    @Override
    public void run() {
        if (cpu.isIdle()) this.schedule();
    }


}
