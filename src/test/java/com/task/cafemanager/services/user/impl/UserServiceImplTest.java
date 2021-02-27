package com.task.cafemanager.services.user.impl;

import com.sun.jdi.request.DuplicateRequestException;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.entities.enums.Role;
import com.task.cafemanager.data.repositories.UserRepository;
import com.task.cafemanager.services.AbstractUnitTest;
import com.task.cafemanager.services.user.model.UserModificationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends AbstractUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create() {
        String username = "username";
        UserModificationRequest request = new UserModificationRequest(username, "firstName",
                "lastName", "pass", Role.MANAGER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(it -> it.getArgument(0));

        User user = userService.create(request);
        assertThat(user).isNotNull();

        verify(userRepository).findByUsername(username);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_userExists() {
        String username = "username";
        UserModificationRequest request = new UserModificationRequest(username, "firstName",
                "lastName", "pass", Role.MANAGER);
        User user = new User();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThatExceptionOfType(DuplicateRequestException.class)
                .isThrownBy(() -> userService.create(request));

        verify(userRepository).findByUsername(username);

    }

    @Test
    void get() {
        final Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        User user = userService.get(id);
        assertThat(user).isNotNull();

        verify(userRepository).findById(id);
    }

    @Test
    void update() {
        final Long id = 1L;
        UserModificationRequest request = new UserModificationRequest("username", "firstName",
                "lastName", "pass", Role.MANAGER);

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenAnswer(it -> it.getArgument(0));

        User user = userService.update(id, request);
        assertThat(user).isNotNull();

        verify(userRepository).findById(id);
        verify(userRepository).save(any(User.class));

    }

    @Test
    void delete() {
        final Long id = 1L;

        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.delete(id);

        verify(userRepository).findById(id);
        verify(userRepository).delete(user);


    }
}
