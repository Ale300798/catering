package com.catering.controller;


import com.catering.model.Buffet;
import com.catering.model.Chef;
import com.catering.validator.ChefValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.catering.service.ChefService;

import javax.validation.Valid;
import java.util.List;


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



    @GetMapping("/admin/modificaChefForm")
    public String modifica(@ModelAttribute("buffet") Chef chef, Model model) {
        model.addAttribute("chef", this.chefService.findChefById(chef.getId()));
        return "modificaChefForm";
    }

    @PostMapping("/admin/modificaChef")
    public String modificaChef(@Valid @ModelAttribute("chef") Chef chef, Model model, BindingResult bindingResults){

        if(!bindingResults.hasErrors()) {
            this.chefService.save(chef);
            model.addAttribute("chef", chef);
            return "chefModificatoVisualizza";
        }
        return "modificaChefForm";
    }

    @GetMapping("/visualizzaChefs")
    public String visualizzaChefs(Model model) {
        model.addAttribute("chefs", this.chefService.chefs());
        return "visualizzaChefs";
    }

    @GetMapping("/visualizzaChef/{id}")
    public String visualizzaChef(Model model, @PathVariable("id") Long id) {
        Chef chef = this.chefService.findChefById(id);
        List<Buffet> buffetProposti = chef.getBuffetProposti();
        model.addAttribute("chef", chef);
        model.addAttribute("buffetProposti", buffetProposti );
        return "visualizzaChef";
    }

}
