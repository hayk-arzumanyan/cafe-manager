package com.task.cafemanager.api.facade.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

@Data
public class UserModificationRequestDto {

    private String username;
    private String firstName;
    private String lastName;
    @JsonProperty("password")
    private String passwordHash;
    private Role role;

}
