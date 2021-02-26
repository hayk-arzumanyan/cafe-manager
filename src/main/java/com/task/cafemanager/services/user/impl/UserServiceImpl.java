package com.task.cafemanager.services.user.impl;

import com.sun.jdi.request.DuplicateRequestException;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.repositories.UserRepository;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.user.UserService;
import com.task.cafemanager.services.user.model.UserModificationRequest;
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

    @Transactional
    @Override
    public User create(UserModificationRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(it -> {
            throw new DuplicateRequestException("Login is already exists!");
        });
        log.info("Creating user by login {}", request.getUsername());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPasswordHash(BCrypt.hashpw(request.getPasswordHash(), BCrypt.gensalt(12)));
        user.setRole(request.getRole());
        final User result = userRepository.save(user);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find the User!"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        log.info("Getting user by username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Cannot find the user"));
    }

    @Transactional
    @Override
    public User update(Long id, UserModificationRequest userModificationRequest) {
        log.info("Getting user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find the user!"));
        log.info("Updating user: {}", user.getUsername());
        user.setUsername(userModificationRequest.getUsername());
        user.setPasswordHash(BCrypt.hashpw(userModificationRequest.getPasswordHash(), BCrypt.gensalt(12)));
        user.setFirstName(userModificationRequest.getFirstName());
        user.setLastName(userModificationRequest.getLastName());
        user.setRole(userModificationRequest.getRole());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(String username) {
        log.info("Deleting user by username: {}", username);
        User result = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Cannot find the user!"));
        userRepository.delete(result);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting user by id: {}", id);
        User result = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find the user!"));
        userRepository.delete(result);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
