package cmp426.schedulers;

import cmp426.Task;

public interface Algorithm {
    void schedule();
    Task pickNextTask();
}
