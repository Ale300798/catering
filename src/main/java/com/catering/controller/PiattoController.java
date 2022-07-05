package com.catering.controller;

import com.catering.model.Chef;
import com.catering.model.Ingrediente;
import com.catering.model.Piatto;
import com.catering.repository.IngredienteRepository;
import com.catering.service.IngredienteService;
import com.catering.service.PiattoService;
import com.catering.validator.PiattoValidator;
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
public class PiattoController {

    @Autowired
    private PiattoService piattoService;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private PiattoValidator piattoValidator;

    @Autowired
    private IngredienteService ingredienteService;


    @GetMapping("/admin/visualizzaPiatti")
    public String visualizzaPiatti(Model model) {
        model.addAttribute("piatti", this.piattoService.piatti());
        return "adminVisualizzaPiatti";
    }
    @PostMapping("/admin/inserimentoPiatto")
    public String salvaPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, Model model, BindingResult bindingResult) {

        if(!bindingResult.hasErrors()){
            this.piattoService.save(piatto);
            model.addAttribute("piatti", this.piattoService.piatti());
            return "adminVisualizzaPiatti";
        }
        return "inserisciPiattoForm";
    }

    @GetMapping("/admin/inserimentoPiattoForm")
    public String aggiungiPiattoForm(Model model) {
        model.addAttribute("piatto", new Piatto());
        return "inserimentoPiattoForm";
    }

    @GetMapping("/admin/modificaPiattoForm/{id}")
    public String modificaPiattoForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("piatto", this.piattoService.findPiattoById(id));
        return "modificaPiattoForm";
    }
    @Transactional
    @PostMapping("/admin/modificaPiatto/{id}")
    public String modificaPiatto(Model model, @Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, @PathVariable("id") Long id) {

        this.piattoValidator.validate(piatto, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.piattoService.updatePiatto(piatto.getNome(), piatto.getDescrizione(), id);
            return this.authenticationController.caricaHomeAdmin(model);
        }
        return "modificaPiattoForm";
    }

    @GetMapping("/admin/visualizzaIngredientiPiatto/{id}")
    public String visualizzaIngredientiPiatto(Model model, @PathVariable("id") Long id) {
        model.addAttribute("ingredienti", this.piattoService.findPiattoById(id).getIngredienti());
        return "adminVisualizzaIngredientiPiatto";
    }

    @Transactional
    @GetMapping("/admin/eliminaPiatto/{id}")
    public String eliminaPiatto(Model model, @PathVariable("id") Long id) {
        this.piattoService.eliminaPiattoDaBuffet(id);
        this.piattoService.eliminaPiattoDaIngredienti(id);
        this.piattoService.deletePiattoById(id);
        return this.authenticationController.caricaHomeAdmin(model);
    }

    @Transactional
    @GetMapping("/admin/eliminaIngredienteDaPiatto/{idP}/{idI}")
    public String eliminaIngredienteDalPiatto(Model model, @PathVariable("idP") Long idP, @PathVariable("idI") Long idI) {

        this.piattoService.eliminaIngredienteDalPiatto(idP, idI);
        model.addAttribute("ingredienti", this.piattoService.findPiattoById(idP).getIngredienti());
        model.addAttribute("id", idP);
        return "adminVisualizzaIngredientiPiatto";
    }

    @GetMapping("/admin/visualizzaIngredientiDisponibili/{id}")
    public String visualizzaIngredientiDisponibili(Model model, @PathVariable("id") Long id) {

        Piatto piatto= this.piattoService.findPiattoById(id);
        List<Long> listaId= new LinkedList<>();

        for(Ingrediente ingrediente : piatto.getIngredienti()) {
            listaId.add(ingrediente.getId());
        }

        if(listaId.size() == 0) {
            model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
        }
        else {
            model.addAttribute("ingredienti", this.piattoService.ingredientiNonDelPiatto(listaId));
        }
        model.addAttribute("nomePiatto", piatto.getNome());
        model.addAttribute("id", id);
        return "aggiungiIngredientiPiattoForm";
    }

    @Transactional
    @GetMapping("/admin/aggiungiIngrediente/{idP}/{idI}")
    public String aggiungiIngrediente(Model model, @PathVariable("idP") Long idP, @PathVariable("idI") Long idI) {
        this.piattoService.inserisciIngredienteAlPiatto(idP, idI);
        model.addAttribute("ingredienti", this.piattoService.findPiattoById(idP).getIngredienti());
        model.addAttribute("id", idP);
        return "adminVisualizzaIngredientiPiatto";
    }
}
