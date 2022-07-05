package com.catering.service;

import com.catering.model.Ingrediente;
import com.catering.model.Piatto;
import com.catering.repository.PiattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PiattoService {

    @Autowired
    private PiattoRepository piattoRepo;

    @Transactional
    public void save(Piatto piatto) {
        this.piattoRepo.save(piatto);
    }

    public int updatePiatto(String nome, String descrizione, Long id) {
        return this.piattoRepo.updatePiatto(nome, descrizione, id);
    }

    public Piatto findPiattoById(Long id) {
        return this.piattoRepo.findById(id).get();
    }

    @Transactional
    public void deletePiattoById(Long id) {
        this.piattoRepo.deleteById(id);
    }

    public List<Piatto> piatti() {
        return this.piattoRepo.findAll();
    }

    public void eliminaPiattoDaBuffet(Long idP) {
        this.piattoRepo.eliminaPiattoDaiBuffet(idP);
    }

    public void eliminaPiattoDaIngredienti(Long idP) {
        this.piattoRepo.eliminaPiattoDaIngredienti(idP);
    }

    public void eliminaIngredienteDalPiatto(Long idP, Long idI) {
        this.piattoRepo.eliminaIngredienteDalPiatto(idP, idI);
    }

    public List<Ingrediente> ingredientiNonDelPiatto(List<Long> listaId) {
        return this.piattoRepo.selectTuttiIngredientiNonDelPiatto(listaId);
    }

    public void inserisciIngredienteAlPiatto(Long idP, Long idI){
        this.piattoRepo.inserisciIngredienteAlPiatto(idP, idI);
    }

    public boolean esisteGia(String nome) {
        return this.piattoRepo.existsByNome(nome);
    }

}
