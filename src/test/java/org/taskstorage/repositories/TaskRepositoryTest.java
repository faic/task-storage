package org.taskstorage.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.taskstorage.AbstractIntegrationTest;
import org.taskstorage.common.TaskPriority;
import org.taskstorage.persistence.Task;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRepositoryTest extends AbstractIntegrationTest {


    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void prepare() {
        taskRepository.deleteAll();
    }

    @Test
    public void findById_whenNotPresent_returnsEmpty() {
        assertTrue(taskRepository.findById(1L).isEmpty());
    }

    @Test
    public void findById_whenPresent_returnsTask() {
        Task task1 = new Task("description", TaskPriority.HIGH);
        task1 = taskRepository.save(task1);

        Optional<Task> result = taskRepository.findById(task1.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), task1.getId());
        assertEquals(result.get().getDescription(), task1.getDescription());
        assertEquals(result.get().getPriority(), task1.getPriority());
        assertEquals(result.get().isCompleted(), task1.isCompleted());
    }

    @Test
    public void findByCompletedAndPriority_whenNone_returnsEmpty() {
        Task task1 = new Task("description", TaskPriority.HIGH);
        Task task2 = new Task("description 2", TaskPriority.LOW);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByCompletedAndPriority(true, TaskPriority.HIGH);

        assertEquals(0, tasks.size());
    }

    @Test
    public void findByCompletedAndPriority_whenHasMatched_returnsMatched() {
        Task task1 = new Task("description", TaskPriority.HIGH);
        Task task2 = new Task("description 2", TaskPriority.LOW);
        Task task3 = new Task("description 3", TaskPriority.LOW);
        task3.setCompleted(true);

        task1 = taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByCompletedAndPriority(false, TaskPriority.HIGH);

        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getId(), task1.getId());
        assertEquals(tasks.get(0).getDescription(), task1.getDescription());
        assertEquals(tasks.get(0).getPriority(), task1.getPriority());
        assertEquals(tasks.get(0).isCompleted(), task1.isCompleted());
    }

    @Test
    public void findByCompleted_whenNoneMatch_returnsEmpty() {
        List<Task> tasks = taskRepository.findByCompleted(false);

        assertEquals(0, tasks.size());
    }

    @Test
    public void findByCompleted_whenHasMatched_returnsMatched() {
        Task task1 = new Task("description", TaskPriority.HIGH);
        Task task2 = new Task("description 2", TaskPriority.LOW);
        task1.setCompleted(true);

        task1 = taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByCompleted(true);

        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getId(), task1.getId());
        assertEquals(tasks.get(0).getDescription(), task1.getDescription());
        assertEquals(tasks.get(0).getPriority(), task1.getPriority());
        assertEquals(tasks.get(0).isCompleted(), task1.isCompleted());
    }

    @Test
    public void findByPriority_whenNoneMatch_returnsEmpty() {
        List<Task> tasks = taskRepository.findByPriority(TaskPriority.HIGH);

        assertEquals(0, tasks.size());
    }

    @Test
    public void findByPriority_whenHasMatched_returnsMatched() {
        Task task1 = new Task("description", TaskPriority.HIGH);
        Task task2 = new Task("description 2", TaskPriority.LOW);

        task1 = taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByPriority(TaskPriority.HIGH);

        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getId(), task1.getId());
        assertEquals(tasks.get(0).getDescription(), task1.getDescription());
        assertEquals(tasks.get(0).getPriority(), task1.getPriority());
        assertEquals(tasks.get(0).isCompleted(), task1.isCompleted());
    }
}
