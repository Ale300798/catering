package com.catering.repository;

import com.catering.model.Piatto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {


    @Modifying
    @Query( "update Piatto p set p.nome = :nome, p.descrizione = :desc where p.id = :id ")
    int updateBuffet(@Param("nome") String nome, @Param("desc") String desc, @Param("id") Long id);


}
