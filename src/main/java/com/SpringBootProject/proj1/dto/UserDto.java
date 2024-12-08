package com.SpringBootProject.proj1.dto;

import com.SpringBootProject.enums.userRole;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private userRole userRoles;
}
