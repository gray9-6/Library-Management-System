package com.example.LibraryManagementSystem.services.author;

import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    List<AuthorResponseDto> getAuthors();
    String updateAuthorById(Long authorId,String name,String biography);
    AuthorResponseDto getAuthorById(Long authorId);
    String deleteAuthorById(Long authorId);
}
