// Nathanael Gutierrez
// Professor Fulakeza
// CMP 426 Operating Systems
// 17 October 2021

package os;

import os.schedulers.FirstComeFirstServedScheduler;
import os.schedulers.RoundRobinScheduler;
import os.schedulers.Scheduler;
import os.schedulers.ShortestJobFirstScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Driver for the Scheduling Algorithms Simulator.
 * <p>
 * This class is responsible for reading
 * the input file, creating the respective
 * Tasks to execute, initializing the CPU simulator,
 * and then simulating the execution of these tasks using
 * the Shortest Job First and Round Robin scheduling
 * algorithms.
 * <p>
 * The simulation is time-based. After every unit of time
 * (by default 1000 ms), the Driver triggers the CPU and
 * the respective Scheduler by calling on their respective
 * tick() methods. At the end of the simulation, the Driver
 * prints the statistics for each Task, displaying their
 * turnaround, response, and wait times, as well as the
 * average value across the entire simulation.
 * <p>
 * To begin the simulation, the Driver should be
 * run on the command line with the input file
 * (or the path to the input file if it is not in the
 * same directory) and the time slice for Round Robin
 * as its first and second arguments, respectively.
 * The Driver will also accept, optionally, an integer
 * representing the number of milliseconds between
 * each tick (by default 1000 ms) as a third argument.
 * This value should be set to 0 if instantaneous
 * execution is desired.
 */
public class Driver {
    /**
     * Amount of time in milliseconds in each "time unit" of the simulation.
     * The simulation clock will "tick" once every time unit.
     */
    private static long DELAY = 1000;

    public static void main(String[] args) throws Exception {
        if (args.length < 2)
            throw new Exception("Please supply the path to the input file and time slice for RR as arguments.");

        // Each scheduler should have their own list of tasks
        // since we change some of their values during execution
        ArrayList<Task> taskList = new ArrayList<>();

        String filename = args[0]; // input file name
        int timeQuantum = Integer.parseInt(args[1]); // time slice for RR
        if (args.length == 3) DELAY = Long.parseLong(args[2]); // set a custom time interval between ticks
        if (timeQuantum < 0 || DELAY < 0)
            throw new IllegalArgumentException("Illegal Argument: Negative command line arguments supplied." +
                    "\nAll command line values must be non-negative.");

        File file = new File(filename);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] taskData = reader.nextLine().split(" "); // Task data should be space-separated in input file

                String PID = taskData[0];
                int arrivalTime = Integer.parseInt(taskData[1]);
                int burstTime = Integer.parseInt(taskData[2]);

                if (arrivalTime < 0 || burstTime < 0) // Tasks should not have negative arrival or burst times
                    throw new IllegalArgumentException("Illegal Argument: Negative value received.");

                // Create a Task object and insert into the task list
                taskList.add(new Task(PID, arrivalTime, burstTime));
            }
        } catch (Exception e) { // Possible exceptions include FileNotFoundException and IllegalArgumentException
            System.err.println(e.getMessage());
            System.err.println("Please make sure the path to your text file is correct and the file is correctly formatted.");
            System.exit(1);
        }

        CPU cpu = new CPU();
        simulateFirstComeFirstServed(cpu, taskList);
        simulateShortestJobFirst(cpu, taskList); // simulate SJF scheduler first
        simulateRoundRobin(cpu, taskList, timeQuantum); // simulate RR scheduler next
        System.out.println("\n------------------------------------------------\n\tProject done by Nathanael Gutierrez\n------------------------------------------------\n");
    }

    /**
     * Runs the simulation with the respective scheduler.
     * <p>
     * The logic for any simulation using any scheduler is the same.
     * After each unit of time:
     * <p>
     * 1. Invoke the scheduler to
     * - queue up any new jobs,
     * - check if a new job should be run,
     * - or, if the scheduler is preemptive, check if a
     * context switch is needed.
     * <p>
     * 2. Invoke the CPU to continue execution of a task.
     * This is akin to the CPU running a new instruction
     * each time the clock ticks. The burst time of the
     * running task should be decremented every time the
     * clock ticks.
     * <p>
     * This cycle should continue as long as the scheduler
     * has pending tasks or the CPU is executing a task.
     * <p>
     * After the simulation is completed, the statistics of
     * each task (their turnaround, response, and wait times),
     * as well as the average value across the entire simulation
     * are printed to the standard output stream.
     *
     * @param cpu       CPU simulator
     * @param taskList  List of Tasks being scheduled and executed
     * @param scheduler Scheduler being used for the simulation
     * @throws InterruptedException via the call to Thread.sleep()
     */
    @SuppressWarnings("BusyWait")
    private static void simulateScheduler(CPU cpu, ArrayList<Task> taskList, Scheduler scheduler) throws InterruptedException {
        cpu.reset(); // reset the clock for each new simulation
        do {
            scheduler.tick(); // invoke the scheduler
            cpu.tick(); // invoke the CPU
            Thread.sleep(DELAY); // wait until it's time for the next tick
        } while (!scheduler.shouldExit() || !cpu.isIdle()); // repeat as long as scheduler has pending tasks or CPU is busy
        cpu.terminate();
        printStatistics(taskList);
        taskList.forEach(Task::reset);
    }

    /**
     * Initiates the simulation with a Shortest Job First Scheduler
     *
     * @param cpu      CPU simulator
     * @param taskList List of tasks being scheduled and executed
     * @throws InterruptedException thrown by the simulator via the call to Thread.sleep()
     */
    private static void simulateShortestJobFirst(CPU cpu, ArrayList<Task> taskList) throws InterruptedException {
        System.out.println("\n------------------------------------------\n\tShortest Job First Scheduling\n------------------------------------------\n");
        ShortestJobFirstScheduler sjf = new ShortestJobFirstScheduler(cpu, taskList);
        simulateScheduler(cpu, taskList, sjf);
    }

    /**
     * Inititates the simulation with a First Come First Served Scheduler
     *
     * @param cpu      CPU simulator
     * @param taskList List of tasks being scheduled and executed
     * @throws InterruptedException thrown by the simulator via the call to Thread.sleep()
     */
    private static void simulateFirstComeFirstServed(CPU cpu, ArrayList<Task> taskList) throws InterruptedException {
        System.out.println("\n------------------------------------------\n\tFirst Come First Served Scheduling\n------------------------------------------\n");
        FirstComeFirstServedScheduler fcfs = new FirstComeFirstServedScheduler(cpu, taskList);
        simulateScheduler(cpu, taskList, fcfs);
    }

    /**
     * Initiates the simulation with a Round-Robin Scheduler
     *
     * @param cpu         CPU simulator
     * @param taskList    List of tasks being scheduled and executed
     * @param timeQuantum The amount of time before the scheduler preempts a running task
     * @throws InterruptedException thrown by the simulator via the call to Thread.sleep()
     */
    private static void simulateRoundRobin(CPU cpu, ArrayList<Task> taskList, int timeQuantum) throws InterruptedException {
        System.out.println("\n------------------------------------------\n\tRound Robin Scheduling\n------------------------------------------\n");
        RoundRobinScheduler rr = new RoundRobinScheduler(cpu, taskList, timeQuantum);
        simulateScheduler(cpu, taskList, rr);
    }

    /**
     * Prints the turnaround, wait, and response times for each task,
     * as well as the average values across all tasks.
     *
     * @param taskList List of tasks ran in the simulation
     */
    private static void printStatistics(ArrayList<Task> taskList) {
        taskList.sort(Comparator.comparing(Task::getPID)); //sort tasks by name (PID)
        // Local class to hold statistic information per process
        // This class calculates the statistics in the constructor,
        // making the code simpler.
        class Statistics {
            final String PID;
            final int turnaround;
            final int wait;
            final int response;

            public Statistics(Task t) {
                this.PID = t.getPID();
                this.turnaround = t.getStopTime() - t.getArrivalTime();
                this.wait = turnaround - t.getBurstTime();
                this.response = t.getStartTime() - t.getArrivalTime();
            }
        }
        int totalTurnaround = 0, totalWait = 0, totalResponse = 0; // sum of all statistic values
        Statistics[] statistics = new Statistics[taskList.size()]; // initialize a new array to hold the statistics per process
        for (int i = 0; i < taskList.size(); i++) { // populate the statistics array in sorted order
            Statistics s = new Statistics(taskList.get(i));
            statistics[i] = s;
            totalTurnaround += s.turnaround;
            totalWait += s.wait;
            totalResponse += s.response;
        }

        // Print all statistics values

        System.out.println("\nTurnaround times:");
        for (Statistics statistic : statistics) {
            System.out.printf("\t%s = %d\n", statistic.PID, statistic.turnaround);
        }
        System.out.println("\nWait times:");
        for (Statistics statistic : statistics) {
            System.out.printf("\t%s = %d\n", statistic.PID, statistic.wait);
        }
        System.out.println("\nResponse times:");
        for (Statistics statistic : statistics) {
            System.out.printf("\t%s = %d\n", statistic.PID, statistic.response);
        }
        double numStatistics = taskList.size();
        System.out.printf("\nAverage turnaround time: %.2f\nAverage wait time: %.2f\nAverage response time: %.2f\n",
                totalTurnaround / numStatistics, totalWait / numStatistics, totalResponse / numStatistics);
    }
}