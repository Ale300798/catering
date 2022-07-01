package com.catering.service;

import com.catering.model.Piatto;
import com.catering.repository.PiattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PiattoService {

    @Autowired
    private PiattoRepository piattoRepo;

    @Transactional
    public void save(Piatto piatto) {
        this.piattoRepo.save(piatto);
    }

    public Piatto findPiattoById(Long id) {
        return this.piattoRepo.findById(id).get();
    }

    @Transactional
    public void deletePiattoById(Long id) {
        this.piattoRepo.deleteById(id);
    }

}
