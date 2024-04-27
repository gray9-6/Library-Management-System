package com.example.LibraryManagementSystem.services.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.models.Rental;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.services.rental.RentalServiceImpl;
import com.example.LibraryManagementSystem.utils.Messages;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private RentalServiceImpl rentalService;


    @InjectMocks
    private BookServiceImpl bookService;

    public BookServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testCreateBook() {
        // Create test data
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setTitle("Test Book");
        requestDto.setPublicationYear(2022);
        requestDto.setAuthorId(1L);

        Author author = new Author();
        author.setId(1L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(1L); // Setting a predefined ID for testing
            return book;
        });

        // Execute the method
        BookResponseDto responseDto = bookService.createBook(requestDto);

        // Verify the result
        assertNotNull(responseDto);
        assertEquals("Test Book", responseDto.getTitle());
        assertEquals(2022, responseDto.getPublicationYear());
    }

    @Test
     void testGetBookById() {
        // Mock data
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsbn("sadfgh");
        book.setAuthor(new Author());
        book.setRental(new Rental());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Execute the method
        BookResponseDto responseDto = bookService.getBookById(1L);

        // Verify the result
        assertEquals(book.getId(), responseDto.getId());
        assertEquals(book.getTitle(), responseDto.getTitle());
    }

    @Test
     void testGetBookById_BookNotFound() {
        // Mock data
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute the method and verify the exception
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
     void testGetAllBooks() {
        // Mock data
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        books.add(book1);
        books.add(book2);

        when(bookRepository.findAll()).thenReturn(books);

        // Execute the method
        List<BookResponseDto> responseDtoList = bookService.getAllBooks();

        // Verify the result
        assertNotNull(responseDtoList);
        assertEquals(2, responseDtoList.size());
    }

    @Test
     void testUpdateBookById() {
        // Mock data
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setPublicationYear(2022);
        requestDto.setAuthorId(2L);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Old Title");
        book.setPublicationYear(2020);
        Author oldAuthor = new Author();
        oldAuthor.setId(1L);
        book.setAuthor(oldAuthor);

        Author newAuthor = new Author();
        newAuthor.setId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(newAuthor));

        // Execute the method
        String result = bookService.updateBookById(1L, requestDto);

        // Verify the result
        assertEquals(Messages.UPDATED, result);
        assertEquals(requestDto.getTitle(), book.getTitle());
        assertEquals(requestDto.getPublicationYear(), book.getPublicationYear());
        assertEquals(newAuthor, book.getAuthor());
    }

    @Test
     void testDeleteBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        Author author = new Author();
        author.setId(1L);
        author.getBooks_written().add(book);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        String result = bookService.deleteBookById(1L);

        assertEquals(Messages.DELETED_SUCCESSFULLY, result);
        verify(bookRepository, times(1)).deleteById(1L);
        assertTrue(author.getBooks_written().isEmpty());
    }

    @Test
     void testGetBooksAvailableForRent() {
        // Mock data
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        books.add(book1);
        books.add(book2);

        when(rentalService.retrieveAllRentalRecords()).thenReturn(new ArrayList<>());
        when(bookRepository.findAll()).thenReturn(books);

        // Execute the method
        List<BookResponseDto> responseDtoList = bookService.getBooksAvailableForRent();

        // Verify the result
        assertNotNull(responseDtoList);
        assertEquals(2, responseDtoList.size());
    }

    @Test
     void testGetBooksByAuthor() {
        // Mock data
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setAuthor(author);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setAuthor(author);

        List<Book> bookList = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        // Execute the method
        List<BookResponseDto> authorBooks = bookService.getBooksByAuthor("Test Author");

        // Verify the result
        assertNotNull(authorBooks);
        assertEquals(2, authorBooks.size());
    }


}
