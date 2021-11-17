package os.schedulers;

import os.Task;

public interface Algorithm {
    void schedule();
    Task pickNextTask();
}
