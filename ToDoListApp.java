import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Task class with Builder Pattern
class Task {
    private String description;
    private boolean completed;
    private String dueDate;

    private Task(TaskBuilder builder) {
        this.description = builder.description;
        this.completed = builder.completed;
        this.dueDate = builder.dueDate;
    }

    public static class TaskBuilder {
        private String description;
        private boolean completed = false; // default
        private String dueDate;

        public TaskBuilder(String description) {
            this.description = description;
        }

        public TaskBuilder setDueDate(String dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskBuilder setCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markPending() {
        this.completed = false;
    }

    @Override
    public String toString() {
        return description + " - " + (completed ? "Completed" : "Pending") + (dueDate != null ? ", Due: " + dueDate : "");
    }
}

// Memento class for undo/redo functionality
class TaskMemento {
    private final List<Task> taskState;

    public TaskMemento(List<Task> taskState) {
        this.taskState = new ArrayList<>(taskState);
    }

    public List<Task> getTaskState() {
        return new ArrayList<>(taskState);
    }
}

// Caretaker class for managing memento states
class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private List<TaskMemento> history = new ArrayList<>();
    private int currentState = -1;

    public void addTask(Task task) {
        saveState();
        tasks.add(task);
    }

    public void markCompleted(String description) {
        saveState();
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                task.markCompleted();
                return;
            }
        }
    }

    public void deleteTask(String description) {
        saveState();
        tasks.removeIf(task -> task.getDescription().equals(description));
    }

    public void viewTasks(String filter) {
        switch (filter) {
            case "all":
                tasks.forEach(System.out::println);
                break;
            case "completed":
                tasks.stream().filter(Task::isCompleted).forEach(System.out::println);
                break;
            case "pending":
                tasks.stream().filter(task -> !task.isCompleted()).forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid filter. Use 'all', 'completed', or 'pending'.");
        }
    }

    public void undo() {
        if (currentState <= 0) {
            System.out.println("No actions to undo.");
            return;
        }
        currentState--;
        tasks = history.get(currentState).getTaskState();
    }

    public void redo() {
        if (currentState >= history.size() - 1) {
            System.out.println("No actions to redo.");
            return;
        }
        currentState++;
        tasks = history.get(currentState).getTaskState();
    }

    private void saveState() {
        // Remove any states after the current state index (redo not possible)
        history = history.subList(0, currentState + 1);
        history.add(new TaskMemento(tasks));
        currentState = history.size() - 1;
    }
}

// Main class to handle user interaction
public class ToDoListApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("\nAvailable commands:");
            System.out.println("1. Add Task");
            System.out.println("2. Mark Task Completed");
            System.out.println("3. Delete Task");
            System.out.println("4. View Tasks");
            System.out.println("5. Undo");
            System.out.println("6. Redo");
            System.out.println("7. Exit");

            System.out.print("Enter command number: ");
            int command = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (command) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter due date (optional, format YYYY-MM-DD): ");
                    String dueDate = scanner.nextLine();
                    Task task = new Task.TaskBuilder(description)
                            .setDueDate(dueDate.isEmpty() ? null : dueDate)
                            .build();
                    taskManager.addTask(task);
                    break;

                case 2:
                    System.out.print("Enter task description to mark as completed: ");
                    String completedDescription = scanner.nextLine();
                    taskManager.markCompleted(completedDescription);
                    break;

                case 3:
                    System.out.print("Enter task description to delete: ");
                    String deleteDescription = scanner.nextLine();
                    taskManager.deleteTask(deleteDescription);
                    break;

                case 4:
                    System.out.println("Available filters:");
                    System.out.println("1. Show all");
                    System.out.println("2. Show completed");
                    System.out.println("3. Show pending");
                    System.out.print("Choose a filter: ");
                    int filter = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    switch (filter) {
                        case 1:
                            taskManager.viewTasks("all");
                            break;
                        case 2:
                            taskManager.viewTasks("completed");
                            break;
                        case 3:
                            taskManager.viewTasks("pending");
                            break;
                        default:
                            System.out.println("Invalid filter.");
                    }
                    break;

                case 5:
                    taskManager.undo();
                    break;

                case 6:
                    taskManager.redo();
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command number.");
            }
        }
    }
}
