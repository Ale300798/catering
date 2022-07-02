package com.catering.service;

import com.catering.model.Ingrediente;
import com.catering.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @Transactional
    public void saveIngrediente(Ingrediente ingrediente) {
        this.ingredienteRepo.save(ingrediente);
    }


    public Ingrediente findIngredienteById(Long id) {
        return this.ingredienteRepo.findById(id).get();
    }

    @Transactional
    public void deleteIngredienteById(Long id) {
        this.ingredienteRepo.deleteById(id);
    }

    public List<Ingrediente> ingredienti() {
        return this.ingredienteRepo.findAll();
    }

}
