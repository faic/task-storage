package org.taskstorage.dtos;

import org.taskstorage.common.TaskPriority;

public class TaskCreateRequestDTO {
    private String description;
    private TaskPriority priority;

    public TaskCreateRequestDTO(String description, TaskPriority priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }
}
