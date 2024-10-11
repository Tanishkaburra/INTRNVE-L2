import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskOrganizer {

    private List<String> tasks;
    private static final String TASK_FILE_NAME = "stored_tasks.txt";

    public TaskOrganizer() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    private void loadTasks() {
        try (BufferedReader taskReader = new BufferedReader(new FileReader(TASK_FILE_NAME))) {
            String taskLine;
            while ((taskLine = taskReader.readLine()) != null) {
                tasks.add(taskLine);
            }
        } catch (IOException e) {
            System.out.println("No saved tasks found, starting a new task list!");
        }
    }

    private void saveTasks() {
        try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter(TASK_FILE_NAME))) {
            for (String task : tasks) {
                taskWriter.write(task);
                taskWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while storing tasks.");
        }
    }

    public void addNewTask(String newTask) {
        if (newTask != null && !newTask.trim().isEmpty()) {
            tasks.add(newTask);
            saveTasks();
            System.out.println("New task added: " + newTask);
        } else {
            System.out.println("Task description cannot be empty.");
        }
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void updateTask(int taskIndex, String updatedDescription) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.set(taskIndex, updatedDescription);
            saveTasks();
            System.out.println("Task updated to: " + updatedDescription);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            String deletedTask = tasks.remove(taskIndex);
            saveTasks();
            System.out.println("Deleted task: " + deletedTask);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public static void main(String[] args) {
        TaskOrganizer taskOrganizer = new TaskOrganizer();
        Scanner inputScanner = new Scanner(System.in);
        String userCommand;

        while (true) {
            System.out.println("\nEnter command (add, list, update, remove, quit):");
            userCommand = inputScanner.nextLine();

            switch (userCommand.toLowerCase()) {
                case "add":
                    System.out.println("Enter the task you want to add:");
                    String taskDescription = inputScanner.nextLine();
                    taskOrganizer.addNewTask(taskDescription);
                    break;

                case "list":
                    taskOrganizer.showAllTasks();
                    break;

                case "update":
                    System.out.println("Enter the task number to update:");
                    int updateIndex = Integer.parseInt(inputScanner.nextLine()) - 1;
                    System.out.println("Enter the updated task:");
                    String updatedTask = inputScanner.nextLine();
                    taskOrganizer.updateTask(updateIndex, updatedTask);
                    break;

                case "remove":
                    System.out.println("Enter the task number to delete:");
                    int removeIndex = Integer.parseInt(inputScanner.nextLine()) - 1;
                    taskOrganizer.deleteTask(removeIndex);
                    break;

                case "quit":
                    System.out.println("Exiting Task Organizer.");
                    inputScanner.close();
                    return;

                default:
                    System.out.println("Unknown command, please try again.");
            }
        }
    }
}
