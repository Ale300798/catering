package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.model.Piatto;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

    @Transactional
    @PostMapping("/admin/modifica/buffet/{id}")
    public String modificaBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, @PathVariable("id") Long id, Model model, BindingResult bindingResults){

        this.buffetValidator.validate(buffet, bindingResults);

        if(!bindingResults.hasErrors()) {
            this.buffetService.updateBuffet(buffet.getNome(), buffet.getDescrizione(), id);
            model.addAttribute("buffet", this.buffetService.findBuffetById(id));
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

    @GetMapping("/admin/visualizzaPiattiDisponibili/{id}")
    public String aggiungiPiattoForm(Model model, @PathVariable("id") Long id) {
        Buffet buffet= this.buffetService.findBuffetById(id);
        List<Long> listaId = new LinkedList<>();
        for( Piatto p : buffet.getPiatti()) {
            listaId.add(p.getId());
        }

        List<Piatto> piatti =  this.buffetService.piattiNonNelBuffet(listaId);
        model.addAttribute("piatti", piatti);
        model.addAttribute("id", id);
        return "aggiungiPiattoBuffetForm";
    }

    @Transactional
    @GetMapping("/admin/aggiungiPiattoBuffet/{idB}/{idP}")
    public String aggiungiPiatto(Model model, @PathVariable("idB") Long idBuffet, @PathVariable("idP") Long idPiatto) {

        this.buffetService.aggiungiPiattoBuffet(idBuffet, idPiatto);
        model.addAttribute("buffet", this.buffetService.findBuffetById(idBuffet));
        return "visualizzaBuffet";

    }


}
