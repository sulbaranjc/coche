package com.example.coche.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ManejadorGlobalDeExcepciones {

    @ExceptionHandler(CocheNoEncontradoException.class)
    public String manejarCocheNoEncontrado(CocheNoEncontradoException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/coches";
    }
}
