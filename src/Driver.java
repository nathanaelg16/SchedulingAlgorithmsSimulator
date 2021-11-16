import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Driver {
    private final static long DELAY = 1000;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Task> RRTaskList = new ArrayList<>();
        ArrayList<Task> SJFTaskList = new ArrayList<>();

        String filename = args[0];
        int timeQuantum = Integer.parseInt(args[1]);

        File file = new File(filename);

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] taskData = reader.nextLine().split(" ");
                RRTaskList.add(new Task(taskData[0], Integer.parseInt(taskData[1]), Integer.parseInt(taskData[2])));
                SJFTaskList.add(new Task(taskData[0], Integer.parseInt(taskData[1]), Integer.parseInt(taskData[2])));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Please make sure the path to your text file is correct and the file is correctly formatted.");
        }


        CPU cpu = new CPU();
        simulateRoundRobin(cpu, RRTaskList, timeQuantum);
        simulateShortestJobFirst(cpu, SJFTaskList);
        System.out.println("\n\n------------------------------------------\n\tProject done by Nathanael Gutierrez\n------------------------------------------\n");
    }

    private static void simulateShortestJobFirst(CPU cpu, ArrayList<Task> taskList) {
        // TODO IMPLEMENTATION
    }

    private static void simulateRoundRobin(CPU cpu, ArrayList<Task> taskList, int timeQuantum) throws InterruptedException {
        System.out.println("------------------------------------------\n\t\tRound Robin Scheduling\n------------------------------------------\n");

        RoundRobin rr = new RoundRobin(cpu, taskList, timeQuantum);

        do {
            rr.tick();
            cpu.tick();
            Thread.sleep(DELAY);
        } while (!rr.shouldExit() || !cpu.isIdle());
        cpu.terminate();
        printStatistics(taskList);
    }

    private static void printStatistics(ArrayList<Task> taskList) {
        taskList.sort(Comparator.comparing(Task::getPID));
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
        int totalTurnaround = 0, totalWait = 0, totalResponse = 0;
        Statistics[] statistics = new Statistics[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            Statistics s = new Statistics(taskList.get(i));
            statistics[i] = s;
            totalTurnaround += s.turnaround;
            totalWait += s.wait;
            totalResponse += s.response;
        }

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
        double numStatistics = (double) taskList.size();
        System.out.printf("\nAverage turnaround time: %.2f\nAverage wait time: %.2f\nAverage response time: %.2f",
                totalTurnaround / numStatistics, totalWait / numStatistics, totalResponse / numStatistics);
    }
}