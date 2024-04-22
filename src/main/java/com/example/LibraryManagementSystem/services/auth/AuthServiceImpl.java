package com.example.LibraryManagementSystem.services.auth;


import com.example.LibraryManagementSystem.dtos.SignupRequestDto;
import com.example.LibraryManagementSystem.dtos.UserResponseDto;
import com.example.LibraryManagementSystem.enums.UserRole;
import com.example.LibraryManagementSystem.models.User;
import com.example.LibraryManagementSystem.repository.UserRepository;
import com.example.LibraryManagementSystem.transformers.UserTransformer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    // create the customer account
    public UserResponseDto createUser (SignupRequestDto signupRequestDto){

        // create the user from the request dto
        User user = User.builder()
                .email(signupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()))
                .name(signupRequestDto.getName())
                .userRole(UserRole.CUSTOMER)       // this api is only for the customer
                .build();

        // save the user
        User savedUser = userRepository.save(user);

        return UserTransformer.UserToUserResponseDto(savedUser);
    }

    public Boolean hasUserWithEmail(String emailId){
        return userRepository.findFirstByEmail(emailId).isPresent();
    }



    @PostConstruct   // so it will automatically get called , after the constructor
    public void createAdminAccount(){  // create the Admin Account

        //create the admin user
        Optional<User> optionalAdminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        // if the adminAccount is null then only create the admin, otherwise admin already exists
        if(optionalAdminAccount.isEmpty()){
            // create the user
            User adminCreated = User.builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .userRole(UserRole.ADMIN)
                    .password(new BCryptPasswordEncoder().encode("admin@123"))
                    .build();
            // save the created admin
            userRepository.save(adminCreated);

            log.info("Admin account created Successfully");
        }

        log.info("Admin Account Already Exists !!");
    }
}

