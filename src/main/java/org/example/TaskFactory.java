package org.example;

public interface TaskFactory {
    Task createTask(String description, String startTime, String endTime, String priority);
}
