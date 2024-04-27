package com.example.LibraryManagementSystem.services.auth;

import com.example.LibraryManagementSystem.enums.UserRole;
import com.example.LibraryManagementSystem.models.User;
import com.example.LibraryManagementSystem.repository.UserRepository;
import com.example.LibraryManagementSystem.services.jwt.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsername(){
        when(userRepository.findFirstByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(User.builder().name("Dummy").email("dummy@gmail.com").password("asdfghjsdfg").userRole(UserRole.CUSTOMER).build()));

        UserDetails user = userDetailsService.loadUserByUsername("Dummy");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("dummy@gmail.com",user.getUsername());
    }
}
