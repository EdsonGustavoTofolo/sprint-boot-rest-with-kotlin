package br.com.edsontofolo.interfaceadapters.controllers.v1.mappers

import br.com.edsontofolo.interfaceadapters.controllers.v1.dtos.PersonHttp
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface PersonMapper {

    @Mapping(target = "id", source = "code")
    fun toDomain(personHttp: PersonHttp): br.com.edsontofolo.entities.Person

    @Mapping(target = "code", source = "id")
    fun fromDomain(person: br.com.edsontofolo.entities.Person): PersonHttp
}