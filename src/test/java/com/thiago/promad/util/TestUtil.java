package com.thiago.promad.util;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.entity.Reu;

import java.util.Arrays;

public class TestUtil {

    public static Processo createProcesso() {
        Processo processo = new Processo();
        processo.setNumeros(Arrays.asList(1L, 2L));
        Reu reu = createReu();
        processo.setReu(reu);
        return processo;
    }

    public static Processo createProcesso2() {
        Processo processo2 = new Processo();
        processo2.setNumeros(Arrays.asList(1L, 2L));
        Reu reu = createReu2();
        processo2.setReu(reu);
        return processo2;
    }

    public static Reu createReu() {
        Reu reu = new Reu();
        reu.setNome("Test Reu");
        return reu;
    }

    public static Reu createReu2() {
        Reu reu = new Reu();
        reu.setNome("Test Reu 2");
        return reu;
    }
}