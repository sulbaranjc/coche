package com.example.coche.controller;

import com.example.coche.model.Coche;
import com.example.coche.model.Combustible;
import com.example.coche.model.Transmision;
import com.example.coche.service.CocheService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/coches")
public class CocheController {

    private final CocheService cocheService;

    public CocheController(CocheService cocheService) {
        this.cocheService = cocheService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("coches", cocheService.listarTodos());
        return "coches/listar";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("coche", new Coche());
        agregarListasDeApoyo(model);
        return "coches/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("coche", cocheService.buscarPorId(id));
        agregarListasDeApoyo(model);
        return "coches/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("coche") Coche coche,
                           BindingResult resultado,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            agregarListasDeApoyo(model);
            return "coches/formulario";
        }
        cocheService.guardar(coche);
        redirectAttributes.addFlashAttribute("mensaje", "Coche guardado correctamente.");
        return "redirect:/coches";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cocheService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Coche eliminado correctamente.");
        return "redirect:/coches";
    }

    private void agregarListasDeApoyo(Model model) {
        model.addAttribute("combustibles", Combustible.values());
        model.addAttribute("transmisiones", Transmision.values());
    }
}
