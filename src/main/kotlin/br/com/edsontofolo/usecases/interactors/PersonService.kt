package br.com.edsontofolo.usecases.interactors

import br.com.edsontofolo.entities.Person

interface PersonService {
    fun findById(id: Long): Person
    fun findAll(): List<Person>
    fun create(person: Person): Person
    fun update(person: Person)
    fun delete(id: Long)
}