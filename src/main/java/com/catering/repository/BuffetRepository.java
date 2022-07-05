package com.catering.repository;

import com.catering.model.Buffet;
import com.catering.model.Chef;
import com.catering.model.Piatto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

    public Optional<Buffet> findById(Long id);
    public List<Buffet> findAll();

    public Buffet findByNomeAndDescrizione(String nome, String descrizione);

    public boolean existsById(Long id);

    @Modifying
    @Query( "update Buffet b set b.nome = :nome, b.descrizione = :desc where b.id = :id ")
    int updateBuffet(@Param("nome") String nome, @Param("desc") String desc, @Param("id") Long id);


    @Query(value = "select p from Piatto p where p.id not in :listaPiattiBuffet")
    List<Piatto> selectTuttiIPiattiNonDelBuffet(@Param("listaPiattiBuffet") List<Long> listaPiatti);

    @Modifying
    @Query(value = "insert into buffet_piatti (buffet_id, piatti_id) Values (:idB, :idP)", nativeQuery = true)
    void aggiungiPiattoBuffet(@Param("idB") Long idB, @Param("idP") Long idp );

    @Modifying
    @Query(value = "delete from buffet_piatti bp where bp.buffet_id= :idB and bp.piatti_id = :idP", nativeQuery = true)
    void eliminaPiattoDaBuffet(@Param("idB") Long idB, @Param("idP") Long idp);


    @Modifying
    @Query(value = "delete from buffet_piatti bp where bp.buffet_id= :idB", nativeQuery = true)
    void eliminaPiattiBuffet(@Param("idB") Long idB);

    @Modifying
    @Query(value = "delete from buffet_piatti bp where bp.buffet_id= :idB and bp.piatti_id = :idP", nativeQuery = true)
    void eliminaPiattoBuffet(@Param("idB") Long idB, @Param("idP") Long idP);

    @Modifying
    @Query(value = "delete from chef_buffet_proposti cbp where cbp.buffet_proposti_id = :id", nativeQuery = true)
    void eliminaBuffetDaChef(@Param("id") Long id);

    @Modifying
    @Query(value = "update Buffet b set b.chef = :chef where b.id = :id ")
    int aggiungiChefABuffet(@Param("chef") Chef chef, @Param("id") Long id);

    @Modifying
    @Query(value = " insert into chef_buffet_proposti (chef_id, buffet_proposti_id) Values (:idC, :idB)", nativeQuery = true)
    void aggiungiBuffetAChef(@Param("idC") Long idC, @Param("idB") Long idB);
}
