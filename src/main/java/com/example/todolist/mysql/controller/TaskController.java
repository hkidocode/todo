package com.example.todolist.mysql.controller;

import com.example.todolist.exception.TaskNotExistException;
import com.example.todolist.mysql.dto.TaskDTO;
import com.example.todolist.mysql.model.Task;
import com.example.todolist.mysql.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTOList = taskService.getAll().stream().map(TaskDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(taskDTOList);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getById(taskId));
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody @Valid Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addOrUpdate(task));
    }

    @PutMapping("{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId, @RequestBody @Valid Task task) {
        Task targetedTask = taskService.getById(taskId);
        if (targetedTask != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.addOrUpdate(task));
        }
        throw new TaskNotExistException("Task entity does not exist");
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<HttpStatus> deleteVoyage(@PathVariable Integer taskId) {
        taskService.deleteById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

