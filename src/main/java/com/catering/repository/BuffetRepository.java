package com.catering.repository;

import com.catering.model.Buffet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

    public Optional<Buffet> findById(Long id);
}
