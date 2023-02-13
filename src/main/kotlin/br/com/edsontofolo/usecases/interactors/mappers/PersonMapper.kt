package br.com.edsontofolo.usecases.interactors.mappers

import br.com.edsontofolo.entities.Person
import br.com.edsontofolo.interfaceadapters.database.entities.PersonEntity
import org.mapstruct.Mapper

@Mapper
interface PersonMapper {

    fun fromRepository(personEntity: PersonEntity): Person

    fun toRepository(person: Person): PersonEntity
}