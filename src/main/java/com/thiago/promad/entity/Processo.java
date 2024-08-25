package com.thiago.promad.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processo_seq")
    @SequenceGenerator(name = "processo_seq", sequenceName = "processo_seq", allocationSize = 1)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "processo_numeros", joinColumns = @JoinColumn(name = "processo_id"))
    @Column(name = "numero")
    @NotEmpty(message = "Números do processo são obrigatórios.")
    private List<Long> numeros;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Reu reu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Long> numeros) {
        this.numeros = numeros;
    }

    public Reu getReu() {
        return reu;
    }

    public void setReu(Reu reu) {
        this.reu = reu;
    }
}