package com.project.demo.services;

import com.project.demo.entities.User;
import com.project.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User login(String username,String password){
        Optional<User> user=userRepository.findByUsername(username);

        if(user.isPresent() && passwordEncoder.matches(password,user.get().getPassword())){
            return user.get();
        }else {
            throw new RuntimeException("Invalid username or password");
        }
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
