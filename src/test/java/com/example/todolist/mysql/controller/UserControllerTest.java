//package com.example.todolist.mysql.controller;
//
//import com.example.todolist.mysql.model.User;
//import com.example.todolist.mysql.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
//    // List of users
//    User user1 = new User(1L, "Mustapha", "test1@gmail.com", "password");
//    User user2 = new User(2L, "Mohamed", "test2@gmail.com", "dfdfdfd");
//
//    @Test
//    void getAllUsersSuccess() throws Exception {
//        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
//        Mockito.when(userService.getAll()).thenReturn(users);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/todo/api/v1/users")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].fullName", is("Mustapha")))
//                .andExpect(jsonPath("$[1].fullName", is("Mohamed")));
//    }
//
//    @Test
//    void getUserSuccess() throws Exception {
//        Mockito.when(userService.getById(user1.getIdUser())).thenReturn(user1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/todo/api/v1/users/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].fullName", is("Mustapha")))
//                .andExpect(jsonPath("$[0].email", is("test1@gmail.com")))
//                .andExpect(jsonPath("$[0].password", is("password")));
//    }
//
//    @Test
//    void addUserSuccess() throws Exception {
//        User newUser = new User(3L, "Kawtar", "test3@gmail.com", "123456");
//
//        Mockito.when(userService.addOrUpdate(newUser)).thenReturn(newUser);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/todo/api/v1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(newUser));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.email", is("test3@gmail.com")));
//
//    }
//
//    @Test
//    void updateUserSuccess() throws Exception {
//        User updateUser = user1;
//        updateUser.setEmail("mostapha@gmail.com");
//
//        Mockito.when(userService.addOrUpdate(updateUser)).thenReturn(updateUser);
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/todo/api/v1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(updateUser));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isAccepted())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.email", is("mostapha@gmail.com")));
//
//    }
//
//    @Test
//    void deleteUserSuccess() throws Exception {
//        Mockito.when(userService.getById(user2.getIdUser())).thenReturn(user2);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/todo/api/v1/users/2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//}