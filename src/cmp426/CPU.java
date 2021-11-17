package cmp426;

public class CPU {
    private Task runningTask;
    private int start;
    private int time = 0;

    public CPU() {
        initialize();
    }

    public Task getRunningTask() {
        return this.runningTask;
    }

    public void run(Task nextTask) {
        if (this.runningTask == nextTask) {
            if (this.runningTask != null && this.runningTask.getBurstTimeRemaining() > 0) return;
            else nextTask = null;
        }

        if (runningTask != null) runningTask.setStopTime(time);
        if (nextTask != null && !nextTask.hasResponded()) nextTask.setStartTime(time);
        this.print(runningTask);
        this.runningTask = nextTask;
        this.start = time;
    }

    public boolean isIdle() {
        return this.runningTask == null || this.runningTask.getBurstTimeRemaining() == 0;
    }

    private void print(Task t) {
        if (this.time == 0) return;
        if (t == null) System.out.println("idle");
        else System.out.printf("[%d-%d]\t%s running\n", this.start, this.time, t.getPID());
    }

    public void tick() {
        if (runningTask != null) {
            runningTask.updateBurstTimeRemaining(1);
        }
        this.time++;
    }

    public long elapsedTime() {
        return this.time - this.start;
    }

    private void initialize() {
        System.out.println("------------------------------------------\n\t\tCPU Scheduling Simulation\n------------------------------------------");
    }

    public void terminate() {
        this.run(null);
    }

    public void reset() {
        this.time = 0;
    }
}
