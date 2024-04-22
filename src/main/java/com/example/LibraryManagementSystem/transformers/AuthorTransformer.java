package com.example.LibraryManagementSystem.transformers;

import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;
import com.example.LibraryManagementSystem.models.Author;

public class AuthorTransformer {

    public static Author authorRequestDtoToAuthor(AuthorRequestDto authorRequestDto){
        return Author.builder()
                .name(authorRequestDto.getName())
                .biography(authorRequestDto.getBiography())
                .build();
    }

    public static AuthorResponseDto authorToAuthorResponseDto(Author author){
        return AuthorResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .build();
    }
}
