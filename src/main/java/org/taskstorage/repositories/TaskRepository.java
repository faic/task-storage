package org.taskstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taskstorage.common.TaskPriority;
import org.taskstorage.persistence.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(long id);
    List<Task> findByCompleted(boolean completed);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByCompletedAndPriority(boolean completed, TaskPriority priority);
}
