package os;

public class Task {
    private final String PID;
    private final int arrivalTime;
    private final int burstTime;
    private int burstTimeRemaining;
    private int startTime;
    private int stopTime;
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

    public boolean hasResponded() {
        return this.responded;
    }

}
