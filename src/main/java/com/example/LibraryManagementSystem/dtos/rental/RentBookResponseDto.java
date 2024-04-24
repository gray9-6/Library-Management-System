package com.example.LibraryManagementSystem.dtos.rental;

import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentBookResponseDto {
    Long id;
    String renterName;
    Date rentalDate;
    Date returnDate;

    BookResponseDto bookResponseDto;
}