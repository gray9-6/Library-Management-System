package com.example.LibraryManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Entity
@Data
@Builder
@Table(name = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Title is Mandatory")
    String title;

    @Column(length = 13, unique = true)
    @NotBlank(message = "isbn is Mandatory")
    String isbn;

    @Column(nullable = false)
    int publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    Author author;

    @OneToOne(fetch = FetchType.LAZY)
    Rental rental;
}
