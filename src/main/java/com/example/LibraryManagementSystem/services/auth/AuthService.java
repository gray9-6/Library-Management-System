package com.example.LibraryManagementSystem.services.auth;


import com.example.LibraryManagementSystem.dtos.SignupRequestDto;
import com.example.LibraryManagementSystem.dtos.UserResponseDto;

public interface AuthService {
    UserResponseDto createUser (SignupRequestDto signupRequestDto);
    Boolean hasUserWithEmail(String emailId);
}
