package com.example.coche.exception;

public class CocheNoEncontradoException extends RuntimeException {

    public CocheNoEncontradoException(Long id) {
        super("No se encontro el coche con id " + id);
    }
}
