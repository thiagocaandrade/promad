package com.thiago.promad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Reu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reu_seq")
    @SequenceGenerator(name = "reu_seq", sequenceName = "reu_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}