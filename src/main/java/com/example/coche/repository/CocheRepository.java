package com.example.coche.repository;

import com.example.coche.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocheRepository extends JpaRepository<Coche, Long> {

    boolean existsByMatricula(String matricula);
}
