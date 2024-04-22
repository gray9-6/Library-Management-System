package com.example.LibraryManagementSystem.dtos.book;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {
    private String title;

    private Long authorId;   // will ask the author id;

    private String isbn;

    private int publicationYear;
}
