package org.taskstorage.persistence;

import org.hibernate.annotations.CreationTimestamp;
import org.taskstorage.common.TaskPriority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @Column(nullable = false)
    private boolean completed;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp created;

    // (for JPA)
    public Task() {
    }

    public Task(String description, TaskPriority priority) {
        this.description = description;
        this.completed = false;
        this.priority = priority;
    }

    public Task(long id, String description, boolean completed, TaskPriority priority, Instant created) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.created = Timestamp.from(created);
    }

    public long getId() {
        return id;
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
        return created.toInstant();
    }
}
