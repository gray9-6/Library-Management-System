package com.example.LibraryManagementSystem.services.rental;

import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;
import com.example.LibraryManagementSystem.exception.BookRentedException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.models.Rental;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createRentRecord_ValidRequest_ReturnRentBookResponseDto() {
        // Arrange
        RentBookRequestDto rentRequestDto = createDummyRentBookRequestDto(1L);

        Book mockBook = createDummyBook(createDummyAuthor());


        Rental mockRental = createDummyRental(mockBook);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        when(rentalRepository.save(any(Rental.class))).thenReturn(mockRental);

        // Act
        RentBookResponseDto responseDto = rentalService.createRentRecord(rentRequestDto);

        // Assert
        assertNotNull(responseDto);
        // Add more assertions as needed
    }

    @Test
    void createRentRecord_BookNotFound_ReturnsResponseStatusException() {
        // Arrange
        RentBookRequestDto rentRequestDto = createDummyRentBookRequestDto(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> rentalService.createRentRecord(rentRequestDto));
    }

    @Test
    void retrieveAllRentalRecords_ReturnListOfRentBookResponseDto() {
        // Arrange
        Rental mockRental = createDummyRental(createDummyBook(createDummyAuthor()));
        when(rentalRepository.findAll()).thenReturn(Collections.singletonList(mockRental));

        // Act
        List<RentBookResponseDto> responseDtoList = rentalService.retrieveAllRentalRecords();

        // Assert
        assertFalse(responseDtoList.isEmpty());
        // Add more assertions as needed
    }


    @Test
    void returnBook_BookNotRented_ReturnsBookRentedException() {
        // Arrange
        Long bookId = 1L;
        Long rentalId = 1L;
        Book mockBook = createDummyBook(createDummyAuthor());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // Act & Assert
        assertThrows(BookRentedException.class, () -> rentalService.returnBook(bookId, rentalId));
    }

    @Test
    void getOverdueRentals_ReturnListOfRentBookResponseDto() {
        // Arrange
        Rental mockRental = createDummyRental(createDummyBook(createDummyAuthor()));
        mockRental.setReturnDate(getDateBeforeCurrentDate(5));
        when(rentalRepository.findAll()).thenReturn(Collections.singletonList(mockRental));

        // Act
        List<RentBookResponseDto> responseDtoList = rentalService.getOverdueRentals();

        // Assert
        assertFalse(responseDtoList.isEmpty());
    }


    public static Author createDummyAuthor() {
        return Author.builder()
                .name("Ajay Yadav")
                .biography("A prolific author with numerous bestsellers.")
                .books_written(new ArrayList<>())
                .build();
    }

    public static Book createDummyBook(Author author) {
        return Book.builder()
                .title("Sample Book")
                .isbn("978-1234567890")
                .publicationYear(2022)
                .author(author)
                .rental(null)
                .build();
    }

    public static Date getReturnDate(Date currentDate,int afterDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, afterDays);
        return calendar.getTime();
    }

    public static Date getDateBeforeCurrentDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    public static Rental createDummyRental(Book book) {
        // Get the current date
        Date currentDate = new Date();

        // Add 14 days to the current date
        Date returnDate = getReturnDate(currentDate,14);

        // Create the rental object with the calculated return date
        return Rental.builder()
                .book(book)
                .renterName("Ajay yadav")
                .rentalDate(currentDate)
                .returnDate(returnDate)
                .build();
    }

    public static RentBookRequestDto createDummyRentBookRequestDto(Long bookId) {
        return RentBookRequestDto.builder()
                .bookId(bookId)
                .renterName("Ajay yadav")
                .build();
    }

}
