package com.example.LibraryManagementSystem.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

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
    Book book;       // this book is managing the relation

    @NotBlank(message = "Renter name is Mandatory")
    String renterName;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date rentalDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

}
