package org.example;

import java.util.Scanner;

public class TaskSchedulerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskFactory taskFactory = new ConcreteTaskFactory();
    private static final ScheduleManager manager = ScheduleManager.getInstance(taskFactory);

    public static void main(String[] args) {
        manager.addObserver(new AppLogger());

        while (true) {
            displayMenu();
            int choice = getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        removeTask();
                        break;
                    case 3:
                        viewAllTasks();
                        break;
                    case 4:
                        viewTasksByPriority();
                        break;
                    case 5:
                        editTask();
                        break;
                    case 6:
                        markTaskAsCompleted();
                        break;
                    case 7:
                        System.out.println("Exiting the application. Goodbye!");
                        System.out.flush();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        System.out.flush();
                }
            } catch (Exception e) {
                System.out.println("\033[31mError: " + e.getMessage() + "\033[0m");
                System.out.flush();
            }

            System.out.println();
            System.out.flush();
        }
    }

    private static void displayMenu() {
        System.out.println("Task Scheduler Menu:");
        System.out.println("1. Add a new task");
        System.out.println("2. Remove a task");
        System.out.println("3. View all tasks");
        System.out.println("4. View tasks by priority");
        System.out.println("5. Edit a task");
        System.out.println("6. Mark a task as completed");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        System.out.flush();
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please try again.");
            System.out.flush();
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }

    private static void addTask() {
        manager.addTask();
        System.out.flush();
    }

    private static void removeTask() {
        manager.removeTask();
        System.out.flush();
    }

    private static void viewAllTasks() {
        manager.viewAllTasks();
        System.out.flush();
    }

    private static void viewTasksByPriority() {
        manager.viewTasksByPriority();
        System.out.flush();
    }

    private static void editTask() throws CloneNotSupportedException {
        manager.editTask();
        System.out.flush();
    }

    private static void markTaskAsCompleted() {
        manager.markTaskAsCompleted();
        System.out.flush();
    }
}