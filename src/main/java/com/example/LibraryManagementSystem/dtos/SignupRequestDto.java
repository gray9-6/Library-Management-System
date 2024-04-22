package com.example.LibraryManagementSystem.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class SignupRequestDto {
    String email;
    String password;
    String name;
}
