package com.task.cafemanager.services.user.model;

import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private final String firstName;
    private final String lastName;
    private final Role role;

}