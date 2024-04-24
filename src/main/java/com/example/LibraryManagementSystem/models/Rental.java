package com.example.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "rental")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(mappedBy = "rental",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Book book;

    String renterName;

    @Temporal(TemporalType.TIMESTAMP)
    Date rentalDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

}
