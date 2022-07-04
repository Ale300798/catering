package com.catering.repository;

import com.catering.model.Ingrediente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

    public List<Ingrediente> findAll();


    @Modifying
    @Query( "update Ingrediente i set i.nome = :nome, i.origine= :origine, i.descrizione = :desc where i.id = :id ")
    int updateIngrediente(@Param("nome") String nome, @Param("origine") String origine, @Param("desc") String desc, @Param("id") Long id);


    @Modifying
    @Query(value = "delete from piatto_ingredienti pi where pi.ingredienti_id= :idI ", nativeQuery = true)
    void eliminaIngredienteDaiPiatti(@Param("idI") Long idI);

}
