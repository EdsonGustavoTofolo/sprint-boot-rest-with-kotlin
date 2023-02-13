package br.com.edsontofolo.interfaceadapters.database.repositories

import br.com.edsontofolo.interfaceadapters.database.entities.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PeopleRepository: JpaRepository<PersonEntity, Long?> {

}