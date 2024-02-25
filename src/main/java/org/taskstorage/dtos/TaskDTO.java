package org.taskstorage.dtos;

import org.taskstorage.common.TaskPriority;

import java.time.Instant;

public class TaskDTO {

    private long id;
    private String description;
    private boolean completed;
    private TaskPriority priority;
    private Instant created;

    public TaskDTO(long id, String description, boolean completed, TaskPriority priority, Instant created) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
