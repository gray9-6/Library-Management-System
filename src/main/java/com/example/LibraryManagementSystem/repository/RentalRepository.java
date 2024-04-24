package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {

}
