package com.example.LibraryManagementSystem.services.rental;

import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;

import java.util.List;

public interface RentalService {
    RentBookResponseDto createRentRecord(RentBookRequestDto rentBookRequestDto);
    List<RentBookResponseDto> retrieveAllRentalRecords();
    List<RentBookResponseDto> retrieveRentalRecordsByAuthorName(String authorName);
    void returnBook(Long bookId,Long rentalId);
    List<RentBookResponseDto> getOverdueRentals();
}
