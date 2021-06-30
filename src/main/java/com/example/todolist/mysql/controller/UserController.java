package com.example.todolist.mysql.controller;

import com.example.todolist.exception.UserNotExistException;
import com.example.todolist.mysql.dto.UserDTO;
import com.example.todolist.mysql.model.User;
import com.example.todolist.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(userId));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addOrUpdate(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid User user) {
        User targetedUser = userService.getById(userId);
        if (targetedUser != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.addOrUpdate(user));
        } else {
            throw new UserNotExistException("User entity does not exist");
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}