package com.example.LibraryManagementSystem.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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
    Book book;

    @NonNull
    String renterName;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date rentalDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

}
