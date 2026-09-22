package com.example.coche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/coches";
    }
}
