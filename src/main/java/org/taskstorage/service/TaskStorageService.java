package org.taskstorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskstorage.common.TaskPriority;
import org.taskstorage.dtos.TaskCreateRequestDTO;
import org.taskstorage.dtos.TaskDTO;
import org.taskstorage.dtos.TaskUpdateRequestDTO;
import org.taskstorage.exceptions.TaskNotFoundException;
import org.taskstorage.mappers.TaskMapper;
import org.taskstorage.persistence.Task;
import org.taskstorage.repositories.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskStorageService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper.INSTANCE::toTaskDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasks(TaskPriority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(TaskMapper.INSTANCE::toTaskDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasks(boolean completed) {
        return taskRepository.findByCompleted(completed)
                .stream()
                .map(TaskMapper.INSTANCE::toTaskDTO)
                .collect(Collectors.toList());

    }

    public List<TaskDTO> getTasks(boolean completed, TaskPriority priority) {
        return taskRepository.findByCompletedAndPriority(completed, priority)
                .stream()
                .map(TaskMapper.INSTANCE::toTaskDTO)
                .collect(Collectors.toList());
    }


    public TaskDTO getTaskById(long taskId) {
        return taskRepository.findById(taskId)
                .map(TaskMapper.INSTANCE::toTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public TaskDTO createTask(TaskCreateRequestDTO createRequest) {
        Task task = new Task(createRequest.getDescription(), createRequest.getPriority());
        return TaskMapper.INSTANCE.toTaskDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(long taskId, TaskUpdateRequestDTO updateRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setCompleted(updateRequest.isCompleted());
        task.setPriority(updateRequest.getPriority());
        task.setDescription(updateRequest.getDescription());
        return TaskMapper.INSTANCE.toTaskDTO(taskRepository.save(task));
    }

    public void deleteTask(long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException(taskId);
        }
    }
}
