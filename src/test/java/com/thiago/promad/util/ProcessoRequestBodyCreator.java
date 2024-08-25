package com.thiago.promad.util;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.requests.ProcessoRequestBody;

public class ProcessoRequestBodyCreator {
    public static ProcessoRequestBody createProcessoRequestBody() {
        Processo processo = TestUtil.createProcesso();
        return new ProcessoRequestBody(processo.getNumeros(), processo.getReu());
    }
}
