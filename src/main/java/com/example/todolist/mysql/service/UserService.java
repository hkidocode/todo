package com.example.todolist.mysql.service;

import com.example.todolist.exception.UserNotExistException;
import com.example.todolist.mysql.model.User;
import com.example.todolist.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getById(Long userId) {
        if (userRepository.findById(userId).isPresent())
            return userRepository.findById(userId).get();
        throw new UserNotExistException("User entity does not exist");
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addOrUpdate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}
