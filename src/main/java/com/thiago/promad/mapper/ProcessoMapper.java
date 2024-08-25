package com.thiago.promad.mapper;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.requests.ProcessoRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProcessoMapper {

    @Mapping(target = "id", ignore = true)
    Processo toProcesso(ProcessoRequestBody processoRequestBody);

    ProcessoRequestBody toProcessoRequestBody(Processo processo);
}
