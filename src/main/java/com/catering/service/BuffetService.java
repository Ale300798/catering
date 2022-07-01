package com.catering.service;

import com.catering.model.Buffet;
import com.catering.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepo;

    public Buffet findBuffetById(Long id) {
        return this.buffetRepo.findById(id).get();
    }


    @Transactional
    public void saveBuffet(Buffet buffet) {
        this.buffetRepo.save(buffet);
    }


    @Transactional
    public void deleteIngredienteById(Long id) {
        this.buffetRepo.deleteById(id);
    }
}
