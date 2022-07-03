package com.catering.repository;

import com.catering.model.Chef;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChefRepository extends CrudRepository<Chef, Long> {


    public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);


    public List<Chef> findAll();


    @Modifying
    @Query( "update Chef c set c.nome = :nome, c.cognome= :cognome, c.nazionalita = :nazionalita where c.id = :id ")
    int updateBuffet(@Param("nome") String nome, @Param("cognome") String cognome, @Param("nazionalita") String nazionalita, @Param("id") Long id);



}
