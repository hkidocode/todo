package com.example.todolist.mysql.service;

import com.example.todolist.exception.EmailNotFoundException;
import com.example.todolist.mysql.model.AppUser;
import com.example.todolist.mysql.model.User;
import com.example.todolist.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userInDB = userRepository.findByEmail(email);
        if (userInDB == null) {
            throw new EmailNotFoundException("Email not found");
        }
        return new AppUser(userInDB);
    }
}
