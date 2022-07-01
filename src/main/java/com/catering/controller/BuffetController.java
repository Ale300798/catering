package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.service.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping("/admin/modificaChefForm")
    public String modifica(@ModelAttribute("buffet") Buffet buffet, Model model) {
        model.addAttribute("chef", this.buffetService.findBuffetById(buffet.getId()));
        return "modificaBuffetForm";
    }

    @PostMapping("/admin/modificaBuffet")
    public String modificaBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, Model model, BindingResult bindingResults){

        if(!bindingResults.hasErrors()) {
            this.buffetService.saveBuffet(buffet);
            model.addAttribute("buffet", buffet);
            return "buffetModificatoVisualizza";
        }
        return "modificaBuffetForm";
    }


}
