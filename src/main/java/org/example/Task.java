package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Task implements Cloneable {
    private static final Logger logger = Logger.getLogger(Task.class.getName());

    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Priority priority;
    private boolean completed;

    public Task(String description, LocalTime startTime, LocalTime endTime, Priority priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.completed = false;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %s [%s]%s",
                startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                description, priority, completed ? " (Completed)" : "");
    }

    public boolean validateTask() {
        if (description == null || description.trim().isEmpty()) {
            logger.warning("Task validation failed: Description cannot be empty.");
            return false;
        }
        if (startTime != null && endTime != null && !startTime.isBefore(endTime)) {
            logger.warning("Task validation failed: Start time must be before end time.");
            return false;
        }
        return true;
    }
}
