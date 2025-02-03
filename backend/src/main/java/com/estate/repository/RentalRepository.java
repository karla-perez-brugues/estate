package com.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estate.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
