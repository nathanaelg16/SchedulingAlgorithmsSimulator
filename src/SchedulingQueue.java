public class SchedulingQueue implements Queue<Task> {
    private TaskNode root;
    private TaskNode last;

    @Override
    public void enqueue(Task task) {
        if (this.isEmpty()) {
            root = new TaskNode(task);
            last = root;
        } else {
            last.next = new TaskNode(task);
            last = last.next;
        }
    }

    @Override
    public Task dequeue() {
        if (this.isEmpty()) return null;
        Task task = root.getJob();
        root = root.next;
        return task;
    }

    @Override
    public Task front() {
        if (this.isEmpty()) return null;
        return root.getJob();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    class TaskNode {
        private final Task task;
        public TaskNode next;

        public TaskNode(Task task) {
            this.task = task;
            this.next = null;
        }

        public Task getJob() {
            return task;
        }
    }
}
