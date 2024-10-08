package org.example;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcreteTaskFactory implements TaskFactory {
    private static final Logger logger = Logger.getLogger(ConcreteTaskFactory.class.getName());

    @Override
    public Task createTask(String description, String startTime, String endTime, String priority) {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            Priority prio = Priority.valueOf(priority.toUpperCase());
            Task task = new ConcreteTask();
            task.setDescription(description);
            task.setStartTime(start);
            task.setEndTime(end);
            task.setPriority(prio);
            if (task.validateTask()) {
                return task;
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error creating task: " + description, e);
        }
        return null;
    }
}