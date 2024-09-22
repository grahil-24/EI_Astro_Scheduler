package org.example;

import java.time.LocalTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class ScheduleManager {

    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName());
    private static ScheduleManager instance;
    private List<Task> tasks;
    private List<TaskObserver> observers;

    // Private constructor to enforce singleton pattern
    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Singleton instance access method
    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    // Add an observer to notify for changes
    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    // Notify all observers about a task update
    private void notifyObservers(String message) {
        for (TaskObserver observer : observers) {
            observer.update(message);
        }
    }

    // Add a new task to the schedule
    public void addTask() throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();
        System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine();

        Task newTask = TaskFactory.createTask(description, startTime, endTime, priority);
        if(newTask == null) {
            throw new IllegalArgumentException("Task could not be created");
        }
        System.out.println("Task added successfully.");

        if(!checkForConflict(newTask)){
            throw new IllegalArgumentException("Task conflicts with existing task: " + newTask.getDescription());
        }

        tasks.add(newTask);
        tasks.sort(Comparator.comparing(Task::getStartTime));  // Sort tasks by start time
        notifyObservers("New task added: " + newTask.getDescription());
    }

    // Remove a task from the schedule
    public void removeTask() {
        if(tasks.isEmpty()) {
            logger.info("No task created");
            return;
        }
        viewAllTasks();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the task number to be deleted: ");
        int taskNumber = scanner.nextInt();
        tasks.remove(taskNumber-1);
        logger.info("Task successfully removed");
    }

    public void viewAllTasks() {
        if(tasks.isEmpty()){
            logger.info("No task created");
        }
        for(int i = 0; i < tasks.size(); i++) {
            System.out.println(i+1 + ": " + tasks.get(i));
        }
    }

    public void viewTasksByPriority() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter priority to view (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine();
        Priority priority_enum = Priority.valueOf(priority.toUpperCase());
        List<Task> prior_tasks = tasks.stream()
                .filter(task -> task.getPriority() == priority_enum)
                .sorted(Comparator.comparing(Task::getStartTime))
                .toList();

        if (prior_tasks.isEmpty()) {
            logger.info("No tasks found with priority " + priority);
        } else {
            System.out.println("Tasks with priority " + priority + ":");
            prior_tasks.forEach(System.out::println);
        }

    }

    public void editTask() throws CloneNotSupportedException {

        viewAllTasks();
        if(tasks.isEmpty()){
            logger.info("No task created");
        }
        Scanner scanner = new Scanner(System.in);
        int taskNumber;
        while(true){
            System.out.print("Enter the task to be edited: ");
            taskNumber = scanner.nextInt();
            if(taskNumber < 1 || taskNumber > tasks.size()){
                logger.warning("Invalid task number");
                continue;
            }
            break;
        }

        scanner.nextLine();
        Task task = tasks.get(taskNumber-1).clone();
        System.out.print("Enter updated task description or press enter to use older("+task.getDescription()+"): ");
        String description = scanner.nextLine();
        System.out.print("Enter updated start time (HH:mm) or press enter to use older("+task.getStartTime()+"): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter updated end time (HH:mm) or press enter to use order("+task.getEndTime()+"): ");
        String endTime = scanner.nextLine();
        System.out.print("Enter updated priority (LOW/MEDIUM/HIGH) or press enter to use older("+task.getPriority()+"): ");
        String priority = scanner.nextLine();
        if(!description.trim().isEmpty()){
            task.setDescription(description);
        }
        if(!startTime.trim().isEmpty()){
            task.setStartTime(LocalTime.parse(startTime));
        }
        if(!endTime.trim().isEmpty()){
            task.setEndTime(LocalTime.parse(endTime));
        }
        if(!priority.trim().isEmpty()){
            task.setPriority(Priority.valueOf(priority));
        }
        if(!task.validateTask()){
            throw new IllegalArgumentException("Invalid task");
        }
        if(!checkForConflict(task)){
            throw new IllegalArgumentException("Task conflicts with existing task: " + task.getDescription());
        }
        tasks.set(taskNumber-1, task);
        tasks.sort(Comparator.comparing(Task::getStartTime));
        logger.info("Task successfully edited");
    }

    public boolean checkForConflict(Task newTask){
        for (Task task : tasks) {
            if (newTask.getStartTime().isBefore(task.getEndTime()) && newTask.getEndTime().isAfter(task.getStartTime())) {
               return false;
            }
        }
        return true;
    }

    public void markTaskAsCompleted() {
        Scanner scanner = new Scanner(System.in);
        if(tasks.isEmpty()){
            logger.info("No task created");
            return;
        }
        viewAllTasks();
        while(true){
            System.out.print("Enter the task number to be marked as completed: ");
            int taskNumber = scanner.nextInt();
            if(taskNumber < 1 || taskNumber > tasks.size()){
                logger.warning("Invalid task number");
                continue;
            }
            Task task = tasks.get(taskNumber-1);
            task.setCompleted(true);
            logger.info("Task marked as completed!");
            break;
        }
    }
}