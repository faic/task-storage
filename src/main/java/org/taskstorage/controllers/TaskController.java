package org.taskstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskstorage.common.TaskPriority;
import org.taskstorage.dtos.TaskCreateRequestDTO;
import org.taskstorage.dtos.TaskDTO;
import org.taskstorage.dtos.TaskUpdateRequestDTO;
import org.taskstorage.service.TaskStorageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskStorageService taskStorageService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(
            @RequestParam("priority") Optional<TaskPriority> priority,
            @RequestParam("completed") Optional<Boolean> completed
    ) {
        final List<TaskDTO> tasks;
        if (priority.isPresent() && completed.isPresent()) {
            tasks = taskStorageService.getTasks(completed.get(), priority.get());
        } else if (priority.isPresent()) {
            tasks = taskStorageService.getTasks(priority.get());
        } else if (completed.isPresent()) {
            tasks = taskStorageService.getTasks(completed.get());
        } else {
            tasks = taskStorageService.getAllTasks();
        }
        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<Object> getTaskById(@PathVariable("taskId") long taskId) {
        try {
            return ResponseEntity.ok(taskStorageService.getTaskById(taskId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody TaskCreateRequestDTO createRequest) {
        try {
            return ResponseEntity.ok(taskStorageService.createTask(createRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateTask(
            @PathVariable("taskId") long taskId,
            @RequestBody TaskUpdateRequestDTO updateRequest
    ) {
        try {
            return ResponseEntity.ok(taskStorageService.updateTask(taskId, updateRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable("taskId") long taskId) {
        try {
            taskStorageService.deleteTask(taskId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}