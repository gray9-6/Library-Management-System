package com.example.LibraryManagementSystem.transformers;


import com.example.LibraryManagementSystem.dtos.UserResponseDto;
import com.example.LibraryManagementSystem.models.User;

public class UserTransformer {

    public static UserResponseDto UserToUserResponseDto(User user){
        return  UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .userRole(user.getUserRole())
                .build();
    }
}
