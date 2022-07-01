package com.catering.controller;

import com.catering.model.Chef;
import com.catering.model.Piatto;
import com.catering.service.PiattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PiattoController {

    @Autowired
    private PiattoService piattoService;

    @PostMapping("/admin/salvaPiatto")
    public String salvaPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, Model model, BindingResult bindingResult) {

        if(!bindingResult.hasErrors()){
            this.piattoService.save(piatto);
            model.addAttribute("piatto", piatto);
            return "visualizzaPiattoInserito";
        }
        return "inserisciPiattoForm";
    }


    @GetMapping("/admin/modificaPiattoForm")
    public String modifica(@ModelAttribute("piatto") Piatto piatto, Model model) {
        model.addAttribute("chef", this.piattoService.findPiattoById(piatto.getId()));
        return "modificaPiattoForm";
    }

    @PostMapping("/admin/modificaPiatto")
    public String modificaPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, Model model, BindingResult bindingResults){

        if(!bindingResults.hasErrors()) {
            this.piattoService.save(piatto);
            model.addAttribute("piatto", piatto);
            return "piattoModificatoVisualizza";
        }
        return "modificaPiattoForm";
    }


}
