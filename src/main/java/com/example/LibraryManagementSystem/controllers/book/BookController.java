package com.example.LibraryManagementSystem.controllers.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.services.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping("/addBook")
    public ResponseEntity<?> createBook(@RequestBody BookRequestDto bookRequestDto){
        try {
            BookResponseDto savedBook = bookService.createBook(bookRequestDto);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
