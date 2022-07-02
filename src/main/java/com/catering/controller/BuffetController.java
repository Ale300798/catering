package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.service.BuffetService;
import com.catering.validator.BuffetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BuffetController {

    @Autowired
    private BuffetService buffetService;
    @Autowired
    private BuffetValidator buffetValidator;

    @GetMapping("/admin/modifica/buffet/form/{id}")
    public String modifica(Model model, @PathVariable("id") Long id) {
        Buffet buffet = this.buffetService.findBuffetById(id);
        model.addAttribute("buffet", buffet );
        return "modificaBuffetForm";
    }

    @PostMapping("/admin/modifica/buffet/{id}")
    public String modificaBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, @PathVariable("id") Long id, Model model, BindingResult bindingResults){

        this.buffetValidator.validate(buffet, bindingResults);

        if(!bindingResults.hasErrors()) {
            buffet.setId(id);
            this.buffetService.saveBuffet(buffet);
            model.addAttribute("buffet", buffet);
            return "buffetModificatoVisualizza";
        }
        return "modificaBuffetForm";
    }

    @GetMapping("/visualizzaBuffets")
    public String visualizzaBuffets(Model model) {
        model.addAttribute("buffets", this.buffetService.tuttiBuffet());
        return "visualizzaBuffets";
    }

    @GetMapping("/visualizzaBuffet/{id}")
    public String visualizzaBuffet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("buffet", this.buffetService.findBuffetById(id));

        return "visualizzaBuffet";
    }


}
