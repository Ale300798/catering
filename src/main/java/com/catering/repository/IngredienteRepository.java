package com.catering.repository;

import com.catering.model.Ingrediente;
import org.springframework.data.repository.CrudRepository;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
}
