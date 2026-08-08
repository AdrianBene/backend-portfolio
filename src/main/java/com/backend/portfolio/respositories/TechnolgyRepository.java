package com.backend.portfolio.respositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.portfolio.entities.Technology;

public interface TechnolgyRepository extends JpaRepository<Technology,Long>{

    boolean existsByName(String name);
    Optional<Technology> findByName(String name);

}
