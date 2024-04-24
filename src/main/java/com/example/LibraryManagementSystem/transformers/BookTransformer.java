package com.example.LibraryManagementSystem.transformers;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.models.Book;

public class BookTransformer {

    public static Book bookRequestDtoToBook(BookRequestDto bookRequestDto){
        return Book.builder()
                .title(bookRequestDto.getTitle())
                .publicationYear(bookRequestDto.getPublicationYear())
                .build();
    }

    public static BookResponseDto bookToBookResponseDto(Book book){
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .build();
    }
}
