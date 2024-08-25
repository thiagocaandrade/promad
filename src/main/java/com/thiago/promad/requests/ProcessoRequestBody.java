package com.thiago.promad.requests;

import com.thiago.promad.entity.Reu;

import java.util.List;

public class ProcessoRequestBody {
    private List<Long> numeros;
    private Reu reu;

    public ProcessoRequestBody(List<Long> numeros, Reu reu) {
        this.numeros = numeros;
        this.reu = reu;
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
