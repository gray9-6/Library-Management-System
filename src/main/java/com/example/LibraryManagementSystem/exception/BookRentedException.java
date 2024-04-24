package com.example.LibraryManagementSystem.exception;

public class BookRentedException extends RuntimeException{
    public BookRentedException(String message){
        super(message);
    }
}
