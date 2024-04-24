package com.example.LibraryManagementSystem.models;


import com.example.LibraryManagementSystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email",unique = true,nullable = false)
    String email;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "name",nullable = false)
    String name;

    @Enumerated(value = EnumType.STRING)
    UserRole userRole;

}

