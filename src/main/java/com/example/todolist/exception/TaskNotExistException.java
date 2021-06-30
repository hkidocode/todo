package com.example.todolist.exception;

public class TaskNotExistException extends RuntimeException {
    public TaskNotExistException(String message) {
        super(message);
    }
}
