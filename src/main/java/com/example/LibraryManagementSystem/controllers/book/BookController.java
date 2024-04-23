package com.example.LibraryManagementSystem.controllers.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.services.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookRequestDto bookRequestDto){
        try {
            BookResponseDto savedBook = bookService.createBook(bookRequestDto);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long bookId){
        try {
            BookResponseDto bookResponseDto = bookService.getBookById(bookId);
            return new ResponseEntity<>(bookResponseDto,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getBooks")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){
        try {
            List<BookResponseDto> books = bookService.getAllBooks();
            return new ResponseEntity<>(books,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<String> updateBookById(@PathVariable("id") Long bookId,
                                                 @RequestBody() BookRequestDto bookRequestDto){
        try {
            String status = bookService.updateBookById(bookId,bookRequestDto);
            return new ResponseEntity<>(status,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long bookId){
        try {
            String status = bookService.deleteBookById(bookId);
            return new ResponseEntity<>(status,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
