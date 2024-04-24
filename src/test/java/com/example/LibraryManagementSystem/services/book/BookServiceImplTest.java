package com.example.LibraryManagementSystem.services.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createBook() {
        Author author = Author.builder()
                .id(1L)
                .name("Ajay")
                .biography("My biography")
                .books_written(new ArrayList<>())
                .build();
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title("Science Book")
                .publicationYear(2024)
                .authorId(1L)
                .build();

        Mockito.when(authorRepository.findById(bookRequestDto.getAuthorId())).thenReturn(Optional.ofNullable(author));

        BookResponseDto bookServiceBook = bookService.createBook(bookRequestDto);

        Assertions.assertEquals(1L,bookServiceBook.getId());
        Assertions.assertEquals("Science Book",bookServiceBook.getTitle());

    }

    @Test
    void getBookById() {
    }

    @Test
    void getAllBooks() {
    }

    @Test
    void updateBookById() {
    }

    @Test
    void deleteBookById() {
    }

    @Test
    void getBooksAvailableForRent() {
    }

    @Test
    void getAllRentedBooks() {
    }

    @Test
    void getBooksByAuthor() {
    }
}