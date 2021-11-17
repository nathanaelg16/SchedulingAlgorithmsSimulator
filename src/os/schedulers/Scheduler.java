package os.schedulers;

import os.CPU;
import os.Task;
import os.queues.Queue;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Scheduler implements Algorithm {
    protected CPU cpu;
    protected Queue<Task> queue;
    private final ArrayList<Task> taskList;
    private int taskListIterator;
    private int time;

    public Scheduler(CPU cpu, ArrayList<Task> taskList, Queue<Task> queue) {
        this.cpu = cpu;
        this.queue = queue;
        this.taskList = taskList;
        this.taskList.sort(Comparator.comparingInt(Task::getArrivalTime));
        this.taskListIterator = 0;
        this.time = 0;
    }

    protected void queue(Task t) {
        queue.enqueue(t);
    }

    @Override
    public Task pickNextTask() {
       return queue.dequeue();
    }

    public abstract void run();

    public boolean shouldExit() {
        return queue.isEmpty() && taskListIterator >= taskList.size();
    }

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
