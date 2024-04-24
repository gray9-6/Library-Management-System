package com.example.LibraryManagementSystem.services.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.transformers.AuthorTransformer;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createAuthor() {
        // Given
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("Test Author");
        requestDto.setBiography("Test Biography");

        Author author = AuthorTransformer.authorRequestDtoToAuthor(requestDto);
        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("Test Author");
        savedAuthor.setBiography("Test Biography");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // When
        AuthorResponseDto responseDto = authorService.createAuthor(requestDto);

        // Then
        assertEquals("Test Author", responseDto.getName());
        assertEquals("Test Biography", responseDto.getBiography());
    }

    @Test
    void getAuthors() {
        // Given
        List<Author> authorList = new ArrayList<>();
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setBiography("Test Biography");
        authorList.add(author);

        when(authorRepository.findAll()).thenReturn(authorList);

        // When
        List<AuthorResponseDto> responseDtoList = authorService.getAuthors();

        // Then
        assertEquals(1, responseDtoList.size());
        assertEquals("Test Author", responseDtoList.get(0).getName());
        assertEquals("Test Biography", responseDtoList.get(0).getBiography());
    }

    // Similarly, write test cases for other methods like updateAuthorById, getAuthorById, and deleteAuthorById
}