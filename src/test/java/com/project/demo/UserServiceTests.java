package com.project.demo;

import com.project.demo.entities.User;
import com.project.demo.repositories.UserRepository;
import com.project.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testFindUserByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> userOptional = userService.findByUsername("example_username");
        if (userOptional.isPresent()) {
            User u = userOptional.get();
            // Now you can use the 'user' object for further operations
        } else {
            // Handle the case where the user is not found
        }

    }
}
