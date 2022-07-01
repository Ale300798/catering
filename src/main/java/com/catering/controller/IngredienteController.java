package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.model.Ingrediente;
import com.catering.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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



    @GetMapping("/admin/modificaIngredienteForm")
    public String modifica(@ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
        model.addAttribute("chef", this.ingredienteService.findIngredienteById(ingrediente.getId()));
        return "modificaIngredienteForm";
    }

    @PostMapping("/admin/modificaIngrediente")
    public String modificaIngrediente(@Valid @ModelAttribute("buffet") Ingrediente ingrediente, Model model, BindingResult bindingResults){

        if(!bindingResults.hasErrors()) {
            this.ingredienteService.saveIngrediente(ingrediente);
            model.addAttribute("ingrediente", ingrediente);
            return "ingredienteModificatoVisualizza";
        }
        return "modificaIngredienteForm";
    }
}
