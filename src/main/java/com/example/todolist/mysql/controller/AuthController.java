package com.example.todolist.mysql.controller;

import com.example.todolist.exception.ERoleNotFoundException;
import com.example.todolist.exception.EmailExistException;
import com.example.todolist.mysql.config.jwt.JwtUtils;
import com.example.todolist.mysql.model.AppUser;
import com.example.todolist.mysql.model.ERole;
import com.example.todolist.mysql.model.Role;
import com.example.todolist.mysql.model.User;
import com.example.todolist.mysql.payload.request.LoginRequest;
import com.example.todolist.mysql.payload.request.SignupRequest;
import com.example.todolist.mysql.payload.response.JwtResponse;
import com.example.todolist.mysql.payload.response.MessageResponse;
import com.example.todolist.mysql.repository.RoleRepository;
import com.example.todolist.mysql.repository.UserRepository;
import com.example.todolist.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailExistException("Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getFullName(), signUpRequest.getEmail(), signUpRequest.getPassword());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole == null) {
                throw new ERoleNotFoundException("Role is not found");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                        if (adminRole == null) {
                            throw new ERoleNotFoundException("Role is not found");
                        }
                        roles.add(adminRole);
                        break;
                    case "moderator":
                        Role moderatorRole = roleRepository.findByName(ERole.ROLE_MODERATOR);
                        if (moderatorRole == null) {
                            throw new ERoleNotFoundException("Role is not found");
                        }
                        roles.add(moderatorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                        if (userRole == null) {
                            throw new ERoleNotFoundException("Role is not found");
                        }
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.addOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AppUser userDetails = (AppUser) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUser().getIdUser(),
                userDetails.getUsername(),
                roles));
    }






}
