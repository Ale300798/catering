package com.catering.controller;

import com.catering.model.Buffet;
import com.catering.model.Piatto;
import com.catering.service.BuffetService;
import com.catering.service.ChefService;
import com.catering.service.PiattoService;
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
import java.util.LinkedList;
import java.util.List;

@Controller
public class BuffetController {

    @Autowired
    private BuffetService buffetService;
    @Autowired
    private BuffetValidator buffetValidator;

    @Autowired
    private ChefService chefService;
    @Autowired
    private PiattoService piattoService;

    @GetMapping("/admin/modificaBuffetForm/{id}")
    public String modifica(Model model, @PathVariable("id") Long id) {
        Buffet buffet = this.buffetService.findBuffetById(id);
        model.addAttribute("buffet", buffet );
        return "modificaBuffetForm";
    }

    @Transactional
    @PostMapping("/admin/modificaBuffet/{id}")
    public String modificaBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, @PathVariable("id") Long id, Model model, BindingResult bindingResults){

        this.buffetValidator.validate(buffet, bindingResults);

        if(!bindingResults.hasErrors()) {
            this.buffetService.updateBuffet(buffet.getNome(), buffet.getDescrizione(), id);
            model.addAttribute("buffets", this.buffetService.tuttiBuffet());
            return "adminVisualizzaBuffets";
        }
        return "modificaBuffetForm";
    }

    @GetMapping("/admin/visualizzaBuffets")
    public String visualizzaBuffets(Model model) {
        model.addAttribute("buffets", this.buffetService.tuttiBuffet());
        return "adminVisualizzaBuffets";
    }

    @GetMapping("/admin/visualizzaBuffet/{id}")
    public String visualizzaBuffet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("buffet", this.buffetService.findBuffetById(id));

        return "modificaBuffetForm";
    }

    @GetMapping("/admin/visualizzaPiattiBuffet/{id}")
    public String visualizzaPiattiBuffet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("piatti", this.buffetService.findBuffetById(id).getPiatti());
        return "adminVisualizzaPiattiBuffet";

    }

    @GetMapping("/admin/visualizzaPiattiDisponibili/{id}")
    public String aggiungiPiattoForm(Model model, @PathVariable("id") Long id) {
        Buffet buffet= this.buffetService.findBuffetById(id);
        List<Long> listaId = new LinkedList<>();
        for( Piatto p : buffet.getPiatti()) {
            listaId.add(p.getId());
        }

        if(listaId.size() == 0) {
            model.addAttribute("piatti", this.piattoService.piatti());
        }
        else {
            List<Piatto> piatti = this.buffetService.piattiNonNelBuffet(listaId);
            model.addAttribute("piatti", piatti);
        }
        model.addAttribute("id", id);
        return "aggiungiPiattoBuffetForm";
    }

    @Transactional
    @GetMapping("/admin/aggiungiPiattoBuffet/{idB}/{idP}")
    public String aggiungiPiatto(Model model, @PathVariable("idB") Long idBuffet, @PathVariable("idP") Long idPiatto) {

        this.buffetService.aggiungiPiattoBuffet(idBuffet, idPiatto);
        model.addAttribute("buffet", this.buffetService.findBuffetById(idBuffet));
        return "modificaBuffetForm";

    }
    @Transactional
    @GetMapping("/admin/eliminaPiattoBuffet/{idB}/{idP}")
    public String eliminaPiattoDaBuffet(Model model, @PathVariable("idB") Long idB, @PathVariable("idP") Long idP) {
        this.buffetService.eliminaPiattoBuffet(idB, idP);
        model.addAttribute("piatti", this.buffetService.findBuffetById(idB).getPiatti());
        return "adminVisualizzaPiattiBuffet";
    }

    @Transactional
    @GetMapping("/admin/eliminaBuffet/{id}")
    public String eliminaBuffet(Model model, @PathVariable("id") Long id) {
        this.buffetService.eliminaPiattiBuffet(id);
        this.buffetService.eliminaBuffetDaChef(id);
        this.buffetService.eliminaBuffet(id);
        model.addAttribute("buffets", this.buffetService.tuttiBuffet());
        return "adminVisualizzaBuffets";
    }

    @GetMapping("/admin/aggiungiBuffet")
    public String inserisciBuffet(Model model) {
        model.addAttribute("buffet", new Buffet());
        return "inserimentoBuffetForm";
    }

    @PostMapping("/admin/inserisciChefBuffet")
    public String inserisciChefAlBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, Model model, BindingResult bindingResult) {

        this.buffetValidator.validate(buffet, bindingResult);

        if(!bindingResult.hasErrors()) {
            this.buffetService.saveBuffet(buffet);
            model.addAttribute("id", this.buffetService.findByNomeAndDesc(buffet.getNome(), buffet.getDescrizione()).getId());
            model.addAttribute("chefs", this.chefService.chefs());
            return "adminInserisciChefBuffet";
        }

        return "inserimentoBuffetForm";
    }

    @Transactional
    @GetMapping("/admin/aggiungiChefBuffet/{idB}/{idC}")
    public String aggiungiChefAlBuffet(Model model, @PathVariable("idB") Long idB, @PathVariable("idC") Long idC) {
        this.buffetService.aggiungiChefABuffet(this.chefService.findChefById(idC), idB);
        this.buffetService.aggiungiBuffetAChef(idC, idB);
        model.addAttribute("buffet", this.buffetService.findBuffetById(idB));
        return "modificaBuffetForm";
    }






}
