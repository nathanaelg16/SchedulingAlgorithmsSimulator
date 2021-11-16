import java.util.ArrayList;
import java.util.Comparator;

public class RoundRobin implements Algorithm {
    private final SchedulingQueue queue = new SchedulingQueue();
    private final CPU cpu;
    private final int timeQuantum;
    private final ArrayList<Task> taskList;
    private int taskListIterator;
    private long time;

    public RoundRobin(CPU cpu, ArrayList<Task> taskList, int timeQuantum) {
        this.cpu = cpu;
        this.timeQuantum = timeQuantum;
        this.time = 0;
        this.taskList = taskList;
        this.taskList.sort(Comparator.comparingInt(Task::getArrivalTime));
        this.taskListIterator = 0;
    }

    @Override
    public void schedule() {
        Task currentTask = cpu.getRunningTask();

        if (currentTask != null) {
            if (currentTask.getBurstTimeRemaining() > 0) this.queue(currentTask);
        }

        Task nextTask = pickNextTask();
        cpu.run(nextTask);
    }

    private void queue(Task task) {
        queue.enqueue(task);
    }

    @Override
    public Task pickNextTask() {
        return queue.dequeue();
    }

    public void run() {
        if (cpu.isIdle() || cpu.elapsedTime() % this.timeQuantum == 0) this.schedule();
    }

    @Override
    public boolean shouldExit() {
        return queue.isEmpty() && taskListIterator >= taskList.size();
    }

    @Override
    public void tick() {
        Task t;
        while (taskListIterator < taskList.size() && (t = taskList.get(taskListIterator)).getArrivalTime() == this.time) {
            this.queue(t);
            this.taskListIterator++;
        }
        this.run();
        this.time++;
    }
}
