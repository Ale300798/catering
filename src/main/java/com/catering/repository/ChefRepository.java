package com.catering.repository;

import com.catering.model.Chef;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChefRepository extends CrudRepository<Chef, Long> {


    public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);


    public List<Chef> findAll();

}
