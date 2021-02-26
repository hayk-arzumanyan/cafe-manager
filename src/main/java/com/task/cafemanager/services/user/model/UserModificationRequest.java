package com.task.cafemanager.services.user.model;

import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

@Data
public class UserModificationRequest {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final Role role;

}