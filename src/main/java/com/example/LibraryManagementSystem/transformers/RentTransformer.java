package com.example.LibraryManagementSystem.transformers;

import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;
import com.example.LibraryManagementSystem.models.Rental;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RentTransformer {

    public static Rental bookRentRequestDtoToRent(RentBookRequestDto rentBookRequestDto){
        Date returnDate = Date.from(LocalDate.now().plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Rental.builder()
                .renterName(rentBookRequestDto.getRenterName())
                .rentalDate(new Date())
                .returnDate(returnDate)
                .build();
    }

    public static RentBookResponseDto rentToBookRentResponseDto(Rental rental){
        return RentBookResponseDto.builder()
                .id(rental.getId())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .renterName(rental.getRenterName())
                .build();
    }
}
