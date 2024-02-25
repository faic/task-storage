package org.taskstorage.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long taskId) {
        super(String.format("Task with id %d not found", taskId));
    }
}
