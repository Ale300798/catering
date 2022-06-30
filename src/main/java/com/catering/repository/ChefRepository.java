package com.catering.repository;

import com.catering.model.Chef;
import org.springframework.data.repository.CrudRepository;

public interface ChefRepository extends CrudRepository<Chef, Long> {


    public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
}
