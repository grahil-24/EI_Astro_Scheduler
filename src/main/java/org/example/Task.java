package org.example;

import java.time.LocalTime;

public interface Task {
    String getDescription();
    void setDescription(String description);
    LocalTime getStartTime();
    void setStartTime(LocalTime startTime);
    LocalTime getEndTime();
    void setEndTime(LocalTime endTime);
    Priority getPriority();
    void setPriority(Priority priority);
    boolean isCompleted();
    void setCompleted(boolean completed);
    boolean validateTask();
    Task clone() throws CloneNotSupportedException;
}