public interface Algorithm {
    void schedule();
    Task pickNextTask();
    void tick();
    boolean shouldExit();
}
