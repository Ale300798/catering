package com.catering.service;

import com.catering.model.Ingrediente;
import com.catering.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public int aggiornaIngrediente(String nome, String origine, String descrizione, Long id) {
        return this.ingredienteRepo.updateIngrediente(nome, origine, descrizione, id);
    }

    public void eliminaIngredienteDaiPiatti(Long id) {
        this.ingredienteRepo.eliminaIngredienteDaiPiatti(id);
    }

    public void eliminaIngrediente(Long id) {
        this.ingredienteRepo.deleteById(id);
    }

}
