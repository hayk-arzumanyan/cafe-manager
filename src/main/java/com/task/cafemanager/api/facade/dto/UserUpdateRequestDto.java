package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

    private String firstName;
    private String lastName;
    private Role role;

}
