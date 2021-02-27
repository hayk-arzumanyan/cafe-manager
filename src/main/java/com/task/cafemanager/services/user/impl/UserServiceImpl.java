package com.task.cafemanager.services.user.impl;

import com.sun.jdi.request.DuplicateRequestException;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.repositories.UserRepository;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.user.UserService;
import com.task.cafemanager.services.user.model.UserCreationRequest;
import com.task.cafemanager.services.user.model.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find the User!"));
    }

    @Transactional
    @Override
    public User create(UserCreationRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(it -> {
            throw new DuplicateRequestException("User is already exists!");
        });
        log.info("Creating user by username {}", request.getUsername());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPasswordHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(12)));
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User update(Long id, UserUpdateRequest request) {
        log.info("Getting user by id: {}", id);
        User user = get(id);
        log.info("Updating user: {}", user.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting user by id: {}", id);
        User result = get(id);
        userRepository.delete(result);
    }
}
