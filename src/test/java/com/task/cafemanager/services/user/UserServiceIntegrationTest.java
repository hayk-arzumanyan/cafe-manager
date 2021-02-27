package com.task.cafemanager.services.user;

import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.entities.enums.Role;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.AbstractIntegrationTest;
import com.task.cafemanager.services.user.model.UserCreationRequest;
import com.task.cafemanager.services.user.model.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void create() {
        UserCreationRequest request = new UserCreationRequest("username", "firstName",
                "lastName", "pass", Role.MANAGER);

        final User user = userService.create(request);
        assertThat(user).isNotNull();
        User result = userService.get(user.getId());
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "passwordHash", "tables")
                .isEqualTo(request);
    }

    @Test
    void update() {
        UserCreationRequest creationRequest =
                new UserCreationRequest("username", "firstName",
                        "lastName", "pass", Role.MANAGER);

        User createdUser = userService.create(creationRequest);
        assertThat(createdUser).isNotNull();
        User resultBefore = userService.get(createdUser.getId());
        assertThat(createdUser)
                .usingRecursiveComparison()
                .ignoringFields("id", "passwordHash", "tables")
                .isEqualTo(resultBefore);

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "firstName_edited",
                "lastName_edited",
                Role.WAITER
        );
        User updatedUser = userService.update(createdUser.getId(), updateRequest);
        assertThat(updatedUser).isNotNull();
        User resultAfter = userService.get(updatedUser.getId());
        assertThat(updatedUser)
                .usingRecursiveComparison()
                .ignoringFields("id", "passwordHash", "tables")
                .isEqualTo(resultAfter);
    }

    @Test
    void getUserNotFound() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> userService.get(1L));
    }

    @Test
    void deleteUserNotFound() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> userService.delete(1L));
    }
}
