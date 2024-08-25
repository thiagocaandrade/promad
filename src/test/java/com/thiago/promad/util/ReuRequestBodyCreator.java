package com.thiago.promad.util;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.requests.ReuRequestBody;

public class ReuRequestBodyCreator {
    public static ReuRequestBody createReuRequestBody() {
        Reu reu = TestUtil.createReu();
        return new ReuRequestBody(reu.getId(), reu.getNome());
    }
}
