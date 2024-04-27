package com.example.LibraryManagementSystem.services.rental;

import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;
import com.example.LibraryManagementSystem.exception.BookRentedException;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.models.Rental;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.RentalRepository;
import com.example.LibraryManagementSystem.transformers.BookTransformer;
import com.example.LibraryManagementSystem.transformers.RentTransformer;
import com.example.LibraryManagementSystem.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService{

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BookRepository bookRepository;



    public RentBookResponseDto createRentRecord(RentBookRequestDto rentBookRequestDto) {
        try {
            // Retrieve the book from the repository
            Book book = bookRepository.findById(rentBookRequestDto.getBookId())
                    .orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND));

            // Check if the book is already rented
            if (book.getRental() != null) {
                throw new BookRentedException(Messages.BOOK_ALREADY_RENTED);
            }

            // Extract the rental object from the request DTO
            Rental rentBook = RentTransformer.bookRentRequestDtoToRent(rentBookRequestDto);
            rentBook.setBook(book);

            // Save the rental object
            Rental savedRentRecord = rentalRepository.save(rentBook);


            // Prepare the response DTO from the saved rent object
            RentBookResponseDto rentBookResponseDto = RentTransformer.rentToBookRentResponseDto(savedRentRecord);
            BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(savedRentRecord.getBook());
            rentBookResponseDto.setBookResponseDto(bookResponseDto);

            return rentBookResponseDto;

        } catch (BookNotFoundException | BookRentedException ex) {
            // Handle exceptions and return appropriate HTTP status
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    public List<RentBookResponseDto> retrieveAllRentalRecords(){
        List<Rental> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(rental -> {
                    RentBookResponseDto rentBookResponseDto = RentTransformer.rentToBookRentResponseDto(rental);
                    BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(rental.getBook());
                    rentBookResponseDto.setBookResponseDto(bookResponseDto);
                    return rentBookResponseDto;
                })
                .collect(Collectors.toList());

    }

    public List<RentBookResponseDto> retrieveRentalRecordsByAuthorName(String authorName){
        List<Rental> rentals = rentalRepository.findAll();

        List<RentBookResponseDto> rentBookResponseDtoList = new ArrayList<>();
        for (Rental rental:rentals) {
            String bookAuthor = rental.getBook().getAuthor().getName();
            if(bookAuthor.equals(authorName)){
                // add this rental to rentalList
                RentBookResponseDto rentBookResponseDto = RentTransformer.rentToBookRentResponseDto(rental);
                BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(rental.getBook());
                rentBookResponseDto.setBookResponseDto(bookResponseDto);

                rentBookResponseDtoList.add(rentBookResponseDto);
            }
        }

        return rentBookResponseDtoList;
    }

    public void returnBook(Long bookId,Long rentalId){
        // check if book exists or not
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(Messages.BOOK_NOT_FOUND));

        if(book.getRental() == null){
            throw new BookRentedException(Messages.BOOK_NOT_RENTED);
        }

        // set the return date to the rental record
        Rental rental = rentalRepository.findById(book.getRental().getId()).orElseThrow(()-> new RuntimeException("No Record Found With This Id"));
        if(rental.getId() != rentalId){
            throw new RuntimeException("Book is Not Issued to this rental record");
        }
        rental.setReturnDate(new Date());
        rentalRepository.save(rental);

        // remove the rental id from the book
        book.setRental(null);
        bookRepository.save(book);
    }


    public List<RentBookResponseDto> getOverdueRentals(){
        List<Rental> rentalList = rentalRepository.findAll();

        List<RentBookResponseDto> rentBookResponseDtoList = new ArrayList<>();
        for (Rental rental:rentalList) {
            LocalDate currentDate = LocalDate.now();
            LocalDate returnDate = rental.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(currentDate.isAfter(returnDate) && rental.getBook() != null){
                RentBookResponseDto rentBookResponseDto = RentTransformer.rentToBookRentResponseDto(rental);
                BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(rental.getBook());
                rentBookResponseDto.setBookResponseDto(bookResponseDto);

                rentBookResponseDtoList.add(rentBookResponseDto);
            }
        }

        return rentBookResponseDtoList;
    }


}
