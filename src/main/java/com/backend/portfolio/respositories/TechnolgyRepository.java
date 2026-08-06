package com.backend.portfolio.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.portfolio.entities.Technology;


@Repository
public interface TechnolgyRepository extends JpaRepository<Technology,Long>{

    boolean existsByName(String name);

}
