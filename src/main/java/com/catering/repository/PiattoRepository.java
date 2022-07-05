package com.catering.repository;

import com.catering.model.Ingrediente;
import com.catering.model.Piatto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {


    public List<Piatto> findAll();

    public boolean existsById(Long id);

    @Modifying
    @Query( "update Piatto p set p.nome = :nome, p.descrizione = :desc where p.id = :id ")
    int updatePiatto(@Param("nome") String nome, @Param("desc") String desc, @Param("id") Long id);

    @Modifying
    @Query(value = "delete from buffet_piatti bp where bp.piatti_id= :idP ", nativeQuery = true)
    void eliminaPiattoDaiBuffet(@Param("idP") Long idP);

    @Modifying
    @Query(value = "delete from piatto_ingredienti pi where pi.piatto_id= :idP ", nativeQuery = true)
    void eliminaPiattoDaIngredienti(@Param("idP") Long idP);


    @Modifying
    @Query(value = "delete from piatto_ingredienti pi where pi.piatto_id= :idP and pi.ingredienti_id= :idI ", nativeQuery = true)
    void eliminaIngredienteDalPiatto(@Param("idP") Long idP, @Param("idI") Long idI);

    @Query(value = "select i from Ingrediente i where i.id not in :listaIdIngredientiPiatto")
    List<Ingrediente> selectTuttiIngredientiNonDelPiatto(@Param("listaIdIngredientiPiatto") List<Long> listaIdIngr);

    @Modifying
    @Query(value = " insert into piatto_ingredienti (piatto_id, ingredienti_id) Values (:idP, :idI)", nativeQuery = true)
    void inserisciIngredienteAlPiatto(@Param("idP") Long idP, @Param("idI") Long idI);



}
