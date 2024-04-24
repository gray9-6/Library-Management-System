package com.example.LibraryManagementSystem.controllers.rental;

import com.example.LibraryManagementSystem.dtos.rental.RentBookRequestDto;
import com.example.LibraryManagementSystem.dtos.rental.RentBookResponseDto;
import com.example.LibraryManagementSystem.services.rental.RentalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
public class RentalController {

    Logger logger = LoggerFactory.getLogger( RentalController.class);

    @Autowired
    RentalService rentalService;

    @PostMapping("/book")
    public ResponseEntity<RentBookResponseDto> createRentRecord(@RequestBody @Valid RentBookRequestDto rentBookRequestDto){
        try {
            RentBookResponseDto rentBookResponseDto = rentalService.createRentRecord(rentBookRequestDto);
            return new ResponseEntity<>(rentBookResponseDto, HttpStatus.OK);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<RentBookResponseDto>> retrieveAllRentalRecords(){
        try {
            List<RentBookResponseDto> rentBookResponseDtoList = rentalService.retrieveAllRentalRecords();
            return new ResponseEntity<>(rentBookResponseDtoList,HttpStatus.OK);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/retrieveBy/{authorName}")
    public ResponseEntity<List<RentBookResponseDto>> retrieveRentalRecordsByAuthorName(@PathVariable("authorName") String authorName){
        try {
            List<RentBookResponseDto> rentBookResponseDtoList = rentalService.retrieveRentalRecordsByAuthorName(authorName);
            return new ResponseEntity<>(rentBookResponseDtoList,HttpStatus.OK);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam(value = "bookId",required = true) Long bookId,
                                           @RequestParam(value = "rentalId",required = true) Long rentalId) {
        try {
            rentalService.returnBook(bookId,rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/overDue")
    public ResponseEntity<List<RentBookResponseDto>> getOverdueRentals(){
        try {
            List<RentBookResponseDto> rentBookResponseDtoList = rentalService.getOverdueRentals();
            return new ResponseEntity<>(rentBookResponseDtoList,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
