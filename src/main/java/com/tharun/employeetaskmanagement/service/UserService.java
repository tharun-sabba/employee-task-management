package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}