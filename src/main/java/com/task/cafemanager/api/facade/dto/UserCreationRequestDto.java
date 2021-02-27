package com.task.cafemanager.api.facade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

@Data
public class UserCreationRequestDto {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;

}
