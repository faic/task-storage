package org.taskstorage.dtos;

import org.taskstorage.common.TaskPriority;

public class TaskUpdateRequestDTO {
    private String description;
    private TaskPriority priority;
    private boolean completed;

    public TaskUpdateRequestDTO(String description, TaskPriority priority, boolean completed) {
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }
}
