package com.example.LibraryManagementSystem.controllers.author;

import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;
import com.example.LibraryManagementSystem.services.author.AuthorService;
import com.example.LibraryManagementSystem.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;


    @PostMapping("/create")   // create
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        try{
            AuthorResponseDto authorResponseDto = authorService.createAuthor(authorRequestDto);
            return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getAll")   // read
    public ResponseEntity<List<AuthorResponseDto>> getAuthors(){
        try{
            List<AuthorResponseDto> authorResponseDtoList = authorService.getAuthors();
            return new ResponseEntity<>(authorResponseDtoList,HttpStatus.FOUND);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/updateById/{id}")  // update
    public ResponseEntity<String> updateAuthorById(@PathVariable("id") Long authorId,
                                                   @RequestParam(value = "name",required = false) String name,
                                                   @RequestParam(value = "biography",required = false) String biography){
        try{
            String status = authorService.updateAuthorById(authorId,name,biography);
            return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.SOMETHING_WENT_WRONG);
        }
    }

    @GetMapping("/getById/{id}")  // read
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable("id") Long authorId){
        try {
            AuthorResponseDto authorResponseDto = authorService.getAuthorById(authorId);
            return new ResponseEntity<>(authorResponseDto,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deleteById/{id}")   // delete
    public ResponseEntity<String> deleteAuthorById(@PathVariable("id") Long authorId){
        try {
            String status = authorService.deleteAuthorById(authorId);
            return new ResponseEntity<>(status,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
