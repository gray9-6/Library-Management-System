package com.example.LibraryManagementSystem.services.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto bookRequestDto);
    BookResponseDto getBookById(Long authorId);
    List<BookResponseDto> getAllBooks();
    String updateBookById(Long bookId,BookRequestDto bookRequestDto);
    String deleteBookById(Long bookId);
    List<BookResponseDto> getBooksAvailableForRent();
    List<BookResponseDto> getAllRentedBooks();
    List<BookResponseDto> getBooksByAuthor(String authorName);

}
