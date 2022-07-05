package com.catering.service;

import com.catering.model.Buffet;
import com.catering.model.Chef;
import com.catering.model.Piatto;
import com.catering.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepo;

    public Buffet findBuffetById(Long id) {
        return this.buffetRepo.findById(id).get();
    }

    public Buffet findByNomeAndDesc(String nome, String desc) {
        return this.buffetRepo.findByNomeAndDescrizione(nome, desc);
    }

    public boolean esisteGia(String nome) {
        return this.buffetRepo.existsByNome(nome);
    }


    @Transactional
    public void saveBuffet(Buffet buffet) {
        this.buffetRepo.save(buffet);
    }


    @Transactional
    public void deleteIngredienteById(Long id) {
        this.buffetRepo.deleteById(id);
    }

    public List<Buffet> tuttiBuffet() {
        return this.buffetRepo.findAll();
    }

    public int updateBuffet(String nome, String desc, Long id) {
        return this.buffetRepo.updateBuffet(nome, desc, id);
    }

    public List<Piatto> piattiNonNelBuffet(List<Long> lista) {
        return this.buffetRepo.selectTuttiIPiattiNonDelBuffet(lista);
    }

    public void aggiungiPiattoBuffet(Long idB, Long idP) {
         this.buffetRepo.aggiungiPiattoBuffet(idB, idP);
    }

    public void eliminaPiattiBuffet(Long id) {
        this.buffetRepo.eliminaPiattiBuffet(id);
    }

    public void eliminaBuffetDaChef(Long id) {
        this.buffetRepo.eliminaBuffetDaChef(id);
    }

    public void eliminaPiattoBuffet(Long idB, Long idP) {
        this.buffetRepo.eliminaPiattoBuffet(idB, idP);
    }

    public void eliminaBuffet(Long id) {
        this.buffetRepo.deleteById(id);
    }

    public int aggiungiChefABuffet(Chef chef, Long idB) {
        return this.buffetRepo.aggiungiChefABuffet(chef, idB);
    }

    public void aggiungiBuffetAChef(Long idC, Long idB) {
        this.buffetRepo.aggiungiBuffetAChef(idC, idB);
    }
}
