package com.example.coche.service;

import com.example.coche.model.Coche;

import java.util.List;

public interface CocheService {

    List<Coche> listarTodos();

    Coche buscarPorId(Long id);

    Coche guardar(Coche coche);

    void eliminar(Long id);
}
