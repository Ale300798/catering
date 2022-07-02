package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.model.Ingrediente;
import com.catering.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping("/admin/salvaIngredeinte")
    public String salvaIngredeinte(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, Model model, BindingResult bindingResults) {

        if(!bindingResults.hasErrors()) {
            this.ingredienteService.saveIngrediente(ingrediente);
            model.addAttribute("ingrediente", ingrediente);
            return "visualizzaIngrediente";
        }
        return "inserisciIngredienteForm";
    }

    @GetMapping("/visualizza/Ingredienti")
    public String visualizzaIngredienti(Model model) {
        model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
        return "visualizzaIngredienti";
    }

    @GetMapping("/visualizza/Ingrediente/{id}")
    public String visualizzaIngrediente(Model model, @PathVariable("id") Long id) {
        model.addAttribute("ingrediente", this.ingredienteService.findIngredienteById(id));
        return "visualizzaIngrediente";
    }
}
