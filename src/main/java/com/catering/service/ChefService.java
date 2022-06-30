package com.catering.service;

import com.catering.model.Chef;
import com.catering.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepo;

    @Transactional
    public void save(Chef chef) {
        chefRepo.save(chef);
    }

    public boolean alreadyExists(Chef chef) {
        return this.chefRepo.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());

    }
}
