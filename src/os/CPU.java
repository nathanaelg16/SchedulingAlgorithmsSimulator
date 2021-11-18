// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os;

/**
 * CPU Simulator
 * <p>
 * At each tick, the simulator "executes"
 * the running task by decrementing its
 * burst time by one. This class also
 * keeps track of the time the process
 * started executing and prints it along
 * with its end time to the standard output
 * stream when the task finishes executing.
 */
public class CPU {
    /**
     * The task currently running on the CPU.
     */
    private Task runningTask;

    /**
     * The time the current task began bursting.
     */
    private int start;

    /**
     * The total running time of the simulation.
     */
    private int time = 0;

    public CPU() {
        initialize();
    }

    /**
     * Returns the task currently running on the CPU
     *
     * @return the task currently running on the CPU
     */
    public Task getRunningTask() {
        return this.runningTask;
    }

    /**
     * Runs a new task on the CPU
     * <p>
     * If the "new" task is the same as the running
     * task, and the running task still has some
     * bursting left to go, this method returns and
     * continues executing the running task. But,
     * if the running task has finished executing,
     * then the CPU is "idled" by "running" a null
     * task.
     *
     * @param nextTask the new task to be run
     */
    public void run(Task nextTask) {
        if (this.runningTask == nextTask) {
            if (this.runningTask != null && this.runningTask.getBurstTimeRemaining() > 0)
                return; // continue bursting this task, do not update any start and end values
            else nextTask = null;
        }

        if (runningTask != null) runningTask.setStopTime(time); // stop the current task
        if (nextTask != null && !nextTask.hasResponded()) nextTask.setStartTime(time); // start the new task
        this.printBurstDetails(); // print the starting and ending times for the current burst of the current task
        this.runningTask = nextTask; // update the running task to be the new task
        this.start = time; // update the start time of the burst
    }

    /**
     * Returns true if the CPU is "running" a null task
     * (not running any task) or if the task that was
     * running has already finished running.
     *
     * @return true if the CPU is not running any task or has finished running a task
     */
    public boolean isIdle() {
        return this.runningTask == null || this.runningTask.getBurstTimeRemaining() == 0;
    }

    /**
     * Prints the start and end times for the current burst
     * of the running task to the standard output stream.
     */
    private void printBurstDetails() {
        if (this.time == 0) return; // no task can finish at t=0
        if (this.runningTask == null)
            System.out.printf("[%d-%d]\tidle\n", this.time, this.time); // print the time the CPU has idled
        else
            System.out.printf("[%d-%d]\t%s running\n", this.start, this.time, this.runningTask.getPID()); // print the start and end time for the burst
    }

    /**
     * Continues execution of the running task,
     * akin to the CPU running a new instruction
     * each time the clock ticks.
     * <p>
     * This method decreases the burst time of
     * the running task by one at each tick, and
     * increments the time of the simulation by one.
     */
    public void tick() {
        if (runningTask != null) {
            runningTask.updateBurstTimeRemaining(1);
        }
        this.time++;
    }

    /**
     * Computes the time elapsed since the current task began executing.
     *
     * @return the time elapsed since the current task began executing
     */
    public long elapsedTime() {
        return this.time - this.start;
    }

    private void initialize() {
        System.out.println("------------------------------------------\n\tCPU Scheduling Simulation\n------------------------------------------");
    }

    /**
     * "Terminates" the CPU by running a null task,
     * thereby causing it to update and print the
     * values of the running task.
     */
    public void terminate() {
        this.run(null);
    }

    /**
     * "Resets" the CPU by restarting the clock.
     */
    public void reset() {
        this.time = 0;
    }
}
