package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("unique" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password");
        user.setRole("USER");
        userRepository.save(user);
    }

    @Test
    void testSaveUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testSaveDuplicateEmail() {
        User duplicateUser = new User();
        duplicateUser.setName("Duplicate User");
        duplicateUser.setEmail(user.getEmail());
        duplicateUser.setPassword("password");
        duplicateUser.setRole("USER");

        try {
            userRepository.save(duplicateUser);
        } catch (Exception e) {
            assertTrue(e instanceof org.springframework.dao.DataIntegrityViolationException);
        }
    }
}