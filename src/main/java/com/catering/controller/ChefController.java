package com.catering.controller;


import com.catering.model.Chef;
import com.catering.validator.ChefValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.catering.service.ChefService;

import javax.validation.Valid;


@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;
    @Autowired
    private ChefValidator chefVal;
    @PostMapping("/admin/inserimentoChef")
    public String saveNewChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResults, Model model) {
       chefVal.validate(chef, bindingResults);
        if(!bindingResults.hasErrors()) {
            chefService.save(chef);
            model.addAttribute( "chef", chef);
            return "ChefVisualizza.html";

        }
        return "inserimentoChefForm.html";
    }


    @GetMapping("/ChefFormInserimento")
    public String getFormIns(Model model) {
        model.addAttribute( "chef", new Chef());
        return "inserimentoChefForm.html";
    }

}
