package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.PrioritySchedulingQueue;

import java.util.ArrayList;

public class ShortestJobFirstScheduler extends Scheduler {

    public ShortestJobFirstScheduler(CPU cpu, ArrayList<Task> taskList) {
        super(cpu, taskList, new PrioritySchedulingQueue());
    }

    @Override
    public void schedule() {
        if (!this.queue.isEmpty()) this.cpu.run(this.pickNextTask());
        else this.cpu.run(null);
    }

    @Override
    public void run() {
        if (this.cpu.isIdle()) this.schedule();
    }


}
