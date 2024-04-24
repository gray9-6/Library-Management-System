package com.example.LibraryManagementSystem.dtos.rental;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentBookRequestDto {
    Long bookId;
    String renterName;
    Date rentalDate;
}
