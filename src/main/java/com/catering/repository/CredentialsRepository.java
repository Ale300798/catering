package com.catering.repository;

import java.util.Optional;

import com.catering.model.Credentials;
import org.springframework.data.repository.CrudRepository;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    public Optional<Credentials> findByUsername(String username);

}