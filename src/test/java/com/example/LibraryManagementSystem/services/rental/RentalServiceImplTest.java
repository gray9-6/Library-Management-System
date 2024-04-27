package com.example.LibraryManagementSystem.services.rental;

import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.exception.BookRentedException;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.models.Rental;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.RentalRepository;
import com.example.LibraryManagementSystem.services.rental.RentalServiceImpl;
import com.example.LibraryManagementSystem.transformers.BookTransformer;
import com.example.LibraryManagementSystem.transformers.RentTransformer;
import com.example.LibraryManagementSystem.utils.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class RentalServiceImplTest {

  @InjectMocks
  RentalServiceImpl rentalService;

  @Mock
  BookRepository bookRepository;
  @Mock
  RentalRepository rentalRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void returnBook_BookNotFound_ThrowsException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BookNotFoundException.class, () -> rentalService.returnBook(bookId, 1L));

        // Verify
        verify(rentalRepository, never()).findById(anyLong());
        verify(rentalRepository, never()).save(any(Rental.class));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
     void returnBook_BookNotRented_ThrowsException() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act + Assert
        assertThrows(BookRentedException.class, () -> rentalService.returnBook(bookId, 1L));

        // Verify
        verify(rentalRepository, never()).findById(anyLong());
        verify(rentalRepository, never()).save(any(Rental.class));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
     void returnBook_BookRented_ReturnsBook() {
        // Arrange
        Long bookId = 1L;
        Long rentalId = 1L;
        Date currentDate = new Date();
        Book book = new Book();
        book.setRental(new Rental());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Rental rental = new Rental();
        rental.setId(rentalId);
        when(rentalRepository.findById(book.getRental().getId())).thenReturn(Optional.of(rental));

        // Act
        rentalService.returnBook(bookId, rentalId);

        // Verify
        verify(rentalRepository).findById(book.getRental().getId());
        verify(rentalRepository).save(rental);
        verify(bookRepository).save(book);
    }




}
