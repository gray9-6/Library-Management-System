package com.example.LibraryManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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

    @NonNull
    String title;

    @Column(nullable = false, length = 13, unique = true)
    String isbn;

    @NonNull
    int publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    Author author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    @JsonIgnore
    Rental rental;
}
