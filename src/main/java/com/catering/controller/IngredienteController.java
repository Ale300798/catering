package com.catering.controller;

import com.catering.model.Ingrediente;
import com.catering.service.IngredienteService;
import com.catering.validator.IngredienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private IngredienteValidator ingredienteValidator;

    @PostMapping("/admin/inserisciIngrediente")
    public String salvaIngredeinte(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, Model model, BindingResult bindingResults) {

        if(!bindingResults.hasErrors()) {
            this.ingredienteService.saveIngrediente(ingrediente);
            model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
            return "adminVisualizzaIngredienti";
        }
        return "inserimentoIngredienteForm";
    }

    @GetMapping("/admin/inserisciIngredienteForm")
    public String inserisciIngredienteForm(Model model) {

        model.addAttribute("ingrediente", new Ingrediente());
        return "inserimentoIngredienteForm";
    }

    @GetMapping("/admin/visualizzaIngredienti")
    public String visualizzaIngredienti(Model model) {
        model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
        return "adminVisualizzaIngredienti";
    }

    @GetMapping("/admin/modificaIngredienteForm/{id}")
    public String modificaIngredienteForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("ingrediente", this.ingredienteService.findIngredienteById(id));
        return "modificaIngredienteForm";
    }

    @Transactional
    @PostMapping("/admin/modificaIngrediente/{id}")
    public String aggiornaIngrediente(Model model,
                                      @PathVariable("id") Long id,
                                      @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult) {
        this.ingredienteValidator.validate(ingrediente, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.ingredienteService.aggiornaIngrediente(ingrediente.getNome(), ingrediente.getOrigine(), ingrediente.getDescrizione(), id);
            return this.authenticationController.caricaHomeAdmin(model);
        }
        return "modificaIngredienteForm";
    }
    

    @Transactional
    @GetMapping("/admin/eliminaIngrediente/{id}")
    public String eliminaIngrediente(Model model, @PathVariable("id") Long id) {
        this.ingredienteService.eliminaIngredienteDaiPiatti(id);
        this.ingredienteService.eliminaIngrediente(id);
        return this.authenticationController.caricaHomeAdmin(model);
    }


}
