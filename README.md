# Scheduling Algorithms Simulator

---
This simulator was created for my CMP 426 - Operating Systems project.
It simulates the execution of tasks on the CPU with varying scheduling 
algorithms in real time.
---
## How to run
To run this program from the command line, first compile with `javac`,
then run with `java`, supplying the proper arguments:

```bash
java src/os/Driver /path/to/input_file.txt time_slice
```


### Example
```bash
javac src/os/Driver
java src/os/Driver ./input.txt 2
```

The input file should contain space-separated values containing the Process Name (or Process ID), the arrival time,
and the CPU burst time.

### Example
```text
P0 0 4
P1 1 2
```
A sample input file is provided in the repository.

---
## Note
This program simulates the execution of the tasks in real time. Therefore, a task that whose burst time
is 5 will take 5 real seconds to execute in simulation. You may change the speed of the simulation by supplying
a third argument to the program with the amount of time (in milliseconds) that a simulation second should take.

### Example  
```bash
java os/Driver ./input.txt 2 500
```

The above example will cause each simulation second to last 0.5 real seconds (or 500 milliseconds).
**For instantaneous execution, this value should be set to zero.**

---
## Future Work
In the future I plan to implement additional scheduling algorithms, such as Shortest Remaining Time First,
First-Come First-Served, and Multilevel Feedback Queue.