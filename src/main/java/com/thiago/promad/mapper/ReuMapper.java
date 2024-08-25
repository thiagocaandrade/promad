package com.thiago.promad.mapper;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.requests.ReuRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReuMapper {

    @Mapping(target = "id", ignore = true)
    Reu toReu(ReuRequestBody reuRequestBody);

    ReuRequestBody toReuRequestBody(Reu reu);
}
