package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
    private Set<TableDto> tables;

}
