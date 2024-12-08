package com.SpringBootProject.proj1.service;

import com.SpringBootProject.proj1.dto.SignupRequest;
import com.SpringBootProject.proj1.dto.UserDto;

public interface AuthService {
UserDto createUser(SignupRequest signupRequest);
boolean hasUserWithEmail(String email);
}
