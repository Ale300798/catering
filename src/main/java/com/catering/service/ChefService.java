package com.catering.service;

import com.catering.model.Chef;
import com.catering.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepo;


    @Transactional
    public void save(Chef chef) {
        chefRepo.save(chef);
    }


    @Transactional
    public void deleteIngredienteById(Long id) {
        this.chefRepo.deleteById(id);
    }

    public boolean alreadyExists(Long id) {
        return this.chefRepo.existsById(id);
    }

    public Chef findChefById(Long id) {
        return this.chefRepo.findById(id).get();
    }

    public List<Chef> chefs() {
        return this.chefRepo.findAll();
    }

    public int updateChef(String nome, String cognome, String nazionalita, Long id) {
        return this.chefRepo.updateChef(nome, cognome, nazionalita, id);
    }

    public void eliminaBuffetDelloChef(Long id) {
        this.chefRepo.eliminaBuffetDelloChef(id);
    }

    public void eliminaBuffetDelloChefInBuffet(Long id) {
        this.chefRepo.eliminaBuffetDelloChefInBuffet(id);
    }

    public void eliminaChef(Long id) {
        this.chefRepo.deleteById(id);
    }
}
