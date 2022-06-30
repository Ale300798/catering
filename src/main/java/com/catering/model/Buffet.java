package com.catering.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Buffet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @NotBlank
    private Chef chef;
    @OneToMany
    private List<Piatto> piatti;
    @NotBlank
    private String nome;
    private String descrizione;


}
