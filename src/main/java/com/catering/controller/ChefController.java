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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;
    @Autowired
    private ChefValidator chefVal;
    @Autowired
    private AuthenticationController authenticationController;
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

    @GetMapping("/admin/visualizzaChefs")
    public String visualizzaChefs(Model model) {
        model.addAttribute("chefs", this.chefService.chefs());
        return "adminVisualizzaChefs";
    }

    @GetMapping("/visualizzaChef/{id}")
    public String visualizzaChef(Model model, @PathVariable("id") Long id) {
        Chef chef = this.chefService.findChefById(id);
        List<Buffet> buffetProposti = chef.getBuffetProposti();
        model.addAttribute("chef", chef);
        model.addAttribute("buffetProposti", buffetProposti );
        return "userVisualizzaChef";
    }

    @GetMapping("/admin/visualizzaBuffetProposti/{id}")
    public String visualizzaBuffetProposti(Model model, @PathVariable("id") Long id) {

        model.addAttribute("buffets", this.chefService.findChefById(id).getBuffetProposti());
        return "adminVisualizzaBuffets";
    }

    @GetMapping("/admin/modificaChefForm/{id}")
    public String modificaChefForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("chef", this.chefService.findChefById(id));
        return "modificaChefForm";
    }

    @Transactional
    @PostMapping("/admin/modificaChef/{id}")
    public String modificaChef(Model model, @Valid @ModelAttribute("chef") Chef chef, @PathVariable("id") Long id, BindingResult bindingResult) {

        this.chefVal.validate(chef, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.chefService.updateChef(chef.getNome(), chef.getCognome(), chef.getNazionalita(), id);
            return this.authenticationController.caricaHomeAdmin(model);
        }
        return "modificaChefForm";
    }

    @Transactional
    @GetMapping("/admin/eliminaChef/{id}")
    public String eliminaChef(Model model, @PathVariable("id") Long id) {
        this.chefService.eliminaBuffetDelloChef(id);
        this.chefService.eliminaBuffetDelloChefInBuffet(id);
        this.chefService.eliminaChef(id);
        return this.authenticationController.caricaHomeAdmin(model);
    }





}
