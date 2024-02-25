package org.taskstorage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.taskstorage.common.TaskPriority;
import org.taskstorage.dtos.TaskDTO;
import org.taskstorage.dtos.TaskUpdateRequestDTO;
import org.taskstorage.exceptions.TaskNotFoundException;
import org.taskstorage.persistence.Task;
import org.taskstorage.repositories.TaskRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskStorageServiceTest {

    @Mock
    private TaskRepository taskRepository;


    @InjectMocks
    private TaskStorageService taskStorageService;

    @Test
    void getAllTasks_whenNoTasks_thenReturnEmpty() {
        when(taskRepository.findAll()).thenReturn(List.of());

        List<TaskDTO> result = taskStorageService.getAllTasks();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllTasks_whenHasTask_thenReturnCorrectly() {
        when(taskRepository.findAll())
                .thenReturn(List.of(new Task(1L, "desc", false, TaskPriority.MEDIUM, Instant.ofEpochMilli(0))));

        List<TaskDTO> result = taskStorageService.getAllTasks();

        assertEquals(result.size(), 1);
        assertFalse(result.get(0).isCompleted());
        assertEquals(result.get(0).getId(), 1L);
        assertEquals(result.get(0).getDescription(), "desc");
        assertEquals(result.get(0).getPriority(), TaskPriority.MEDIUM);
        assertEquals(result.get(0).getCreated(), Instant.ofEpochMilli(0));
    }

    @Test
    void getTasks_whenNoTaskWithRequiredPriority_thenReturnEmpty() {
        when(taskRepository.findByPriority(TaskPriority.MEDIUM)).thenReturn(List.of());

        List<TaskDTO> result = taskStorageService.getTasks(TaskPriority.MEDIUM);

        assertTrue(result.isEmpty());
    }

    @Test
    void getTasks_whenHasTaskWithRequiredPriority_thenReturnMatched() {
        when(taskRepository.findByPriority(TaskPriority.MEDIUM))
                .thenReturn(List.of(new Task(1L, "desc", false, TaskPriority.MEDIUM, Instant.ofEpochMilli(0))));

        List<TaskDTO> result = taskStorageService.getTasks(TaskPriority.MEDIUM);

        assertEquals(result.size(), 1);
        assertFalse(result.get(0).isCompleted());
        assertEquals(result.get(0).getId(), 1L);
        assertEquals(result.get(0).getDescription(), "desc");
        assertEquals(result.get(0).getPriority(), TaskPriority.MEDIUM);
        assertEquals(result.get(0).getCreated(), Instant.ofEpochMilli(0));
    }

    @Test
    void getTasks_whenNoCompleted_thenReturnEmpty() {
        when(taskRepository.findByCompleted(true)).thenReturn(List.of());

        List<TaskDTO> result = taskStorageService.getTasks(true);

        assertTrue(result.isEmpty());
    }

    @Test
    void getTasks_whenHasCompleted_thenReturnMatched() {
        when(taskRepository.findByCompleted(true))
                .thenReturn(List.of(new Task(1L, "desc", true, TaskPriority.MEDIUM, Instant.ofEpochMilli(0))));

        List<TaskDTO> result = taskStorageService.getTasks(true);

        assertEquals(result.size(), 1);
        assertTrue(result.get(0).isCompleted());
        assertEquals(result.get(0).getId(), 1L);
        assertEquals(result.get(0).getDescription(), "desc");
        assertEquals(result.get(0).getPriority(), TaskPriority.MEDIUM);
        assertEquals(result.get(0).getCreated(), Instant.ofEpochMilli(0));
    }

    @Test
    void getTasks_whenNoCompletedWithRequiredPriority_thenReturnEmpty() {
        when(taskRepository.findByCompletedAndPriority(true, TaskPriority.LOW)).thenReturn(List.of());

        List<TaskDTO> result = taskStorageService.getTasks(true, TaskPriority.LOW);

        assertTrue(result.isEmpty());
    }

    @Test
    void getTasks_whenHasCompletedWithRequiredPriority_thenReturnMatched() {
        when(taskRepository.findByCompletedAndPriority(true, TaskPriority.LOW))
                .thenReturn(List.of(new Task(1L, "desc", true, TaskPriority.LOW, Instant.ofEpochMilli(0))));

        List<TaskDTO> result = taskStorageService.getTasks(true, TaskPriority.LOW);

        assertEquals(result.size(), 1);
        assertTrue(result.get(0).isCompleted());
        assertEquals(result.get(0).getId(), 1L);
        assertEquals(result.get(0).getDescription(), "desc");
        assertEquals(result.get(0).getPriority(), TaskPriority.LOW);
        assertEquals(result.get(0).getCreated(), Instant.ofEpochMilli(0));
    }

    @Test
    void getTaskById_whenNoTask_thenThrowsTaskNotFoundException() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,() -> taskStorageService.getTaskById(1L));
    }

    @Test
    void findById_whenHasMatched_thenReturnMatched() {
        when(taskRepository.findById(1L))
                .thenReturn(Optional.of((new Task(1L, "desc", true, TaskPriority.LOW, Instant.ofEpochMilli(0)))));

        TaskDTO result = taskStorageService.getTaskById(1L);

        assertTrue(result.isCompleted());
        assertEquals(result.getId(), 1L);
        assertEquals(result.getDescription(), "desc");
        assertEquals(result.getPriority(), TaskPriority.LOW);
        assertEquals(result.getCreated(), Instant.ofEpochMilli(0));
    }

    @Test
    void updateTask_whenNoTask_thenThrowsTaskNotFoundException() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,() -> taskStorageService.updateTask(
                1L,
                new TaskUpdateRequestDTO("desc", TaskPriority.LOW, false)
        ));
    }

    @Test
    void deleteTask_whenNoTask_thenThrowsTaskNotFoundException() {
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotFoundException.class,() -> taskStorageService.deleteTask(1L));
    }

    @Test
    void delete_whenHasMatched_thenDelete() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskStorageService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }
}
