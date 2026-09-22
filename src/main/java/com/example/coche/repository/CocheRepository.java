package com.example.coche.repository;

import com.example.coche.model.Coche;

import java.util.List;
import java.util.Optional;

public interface CocheRepository {

    List<Coche> findAll();

    Optional<Coche> findById(Long id);

    boolean existsById(Long id);

    boolean existsByMatricula(String matricula);

    Coche save(Coche coche);

    void deleteById(Long id);
}
