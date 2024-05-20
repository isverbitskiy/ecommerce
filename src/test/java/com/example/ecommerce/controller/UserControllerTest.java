package com.example.ecommerce.controller;

import com.example.ecommerce.config.PasswordEncoderConfig;
import com.example.ecommerce.config.SecurityConfig;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, JwtUtil.class, PasswordEncoderConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private User user;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(() -> "ROLE_USER")
        );

        when(userDetailsService.loadUserByUsername(user.getEmail())).thenReturn(userDetails);
        jwtToken = "Bearer " + jwtUtil.generateToken(userDetails);
    }

    private RequestPostProcessor bearerToken() {
        return request -> {
            request.addHeader("Authorization", jwtToken);
            return request;
        };
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .with(bearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void shouldGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", 1L)
                        .with(bearerToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .with(bearerToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .with(bearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        verify(userService, times(1)).updateUser(anyLong(), any(User.class));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/users/{id}", 1L)
                        .with(bearerToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyLong());
    }
}