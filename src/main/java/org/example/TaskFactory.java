package org.example;

import java.time.LocalTime;

public class TaskFactory {
    public static Task createTask(String description, String startTime, String endTime, String priority) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        Priority prio = Priority.valueOf(priority.toUpperCase());
        Task task = new Task(description, start, end, prio);
        if(task.validateTask()){
            return task;
        }
//        System.out.println("Invalid task");
        return null;
    }
}
