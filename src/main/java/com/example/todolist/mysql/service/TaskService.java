package com.example.todolist.mysql.service;

import com.example.todolist.exception.TaskNotExistException;
import com.example.todolist.mysql.model.Task;
import com.example.todolist.mysql.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task getById(Integer taskId) {
        if (taskRepository.findById(taskId).isPresent())
            return taskRepository.findById(taskId).get();
        throw new TaskNotExistException("Task entity does not exist");
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task addOrUpdate(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
