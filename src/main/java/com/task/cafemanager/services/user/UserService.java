package com.task.cafemanager.services.user;

import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.services.user.model.UserCreationRequest;
import com.task.cafemanager.services.user.model.UserUpdateRequest;

public interface UserService {
    User get(Long id);

    User create(UserCreationRequest request);

    User update(Long id, UserUpdateRequest request);

    void delete(Long id);
}