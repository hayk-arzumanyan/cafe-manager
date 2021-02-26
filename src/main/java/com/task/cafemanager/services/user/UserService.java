package com.task.cafemanager.services.user;

import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.services.user.model.UserModificationRequest;

public interface UserService {
    User create(UserModificationRequest user);
    User get(Long id);
    User getByUsername(String username);
    User update(Long id, UserModificationRequest userModificationRequest);
    void delete(String username);
    void delete(Long id);
    User save(User user);
}