// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os;

/**
 * This class represents a task
 * that will be run on the CPU during
 * the simulation.
 */
public class Task {

    /**
     * The Process ID (or name) of the task.
     */
    private final String PID;

    /**
     * The time at which the task arrived.
     */
    private final int arrivalTime;

    /**
     * The amount of time the task will need to
     * execute for.
     */
    private final int burstTime;

    /**
     * The amount of time left for the task
     * to finish executing.
     */
    private int burstTimeRemaining;

    /**
     * The time at which the process began executing.
     */
    private int startTime;

    /**
     * The time at which the process finished executing.
     */
    private int stopTime;

    /**
     * True if the task has begun responding.
     * False if the task has never begun execution.
     */
    private boolean responded;

    public Task(String PID, int arrivalTime, int burstTime) {
        this.PID = PID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.burstTimeRemaining = burstTime;
        this.responded = false;
    }

    public String getPID() {
        return PID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTimeRemaining() {
        return burstTimeRemaining;
    }

    public void setBurstTimeRemaining(int burstTimeRemaining) {
        this.burstTimeRemaining = burstTimeRemaining;
    }

    /**
     * Decrements the remaining burst time of the task by
     * the specified value.
     *
     * @param time the amount by which to decrement the remaining burst time of the task
     */
    public void updateBurstTimeRemaining(int time) {
        this.burstTimeRemaining -= time;
        if (this.burstTimeRemaining < 0) this.burstTimeRemaining = 0;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of a task and indicates that
     * the task has begun responding.
     *
     * @param startTime the time at which the task began executing for the first time
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
        this.responded = true;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * Returns true if the task has begun execution at any point in time,
     * or false if the task has never executed
     *
     * @return true if the task has begun execution at any point in time, or false if the task has never executed
     */
    public boolean hasResponded() {
        return this.responded;
    }
}
