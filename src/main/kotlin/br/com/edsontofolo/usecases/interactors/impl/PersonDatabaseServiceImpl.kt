package br.com.edsontofolo.usecases.interactors.impl

import br.com.edsontofolo.entities.Person
import br.com.edsontofolo.usecases.exceptions.ResourceNotFoundException
import br.com.edsontofolo.interfaceadapters.database.repositories.PeopleRepository
import br.com.edsontofolo.usecases.interactors.PersonService
import br.com.edsontofolo.usecases.interactors.mappers.PersonMapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonDatabaseServiceImpl(private val peopleRepository: PeopleRepository) : PersonService {

    private val logger: Logger = Logger.getLogger(PersonService::class.java.name)
    private val personMapper: PersonMapper = Mappers.getMapper(PersonMapper::class.java)

    override fun findById(id: Long): Person {
        logger.info("Finding person by id.")

        val personEntity = peopleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Person not found.") }

        return personMapper.fromRepository(personEntity)
    }

    override fun findAll(): List<Person> {
        logger.info("Find all people.")
        return peopleRepository.findAll().map { person -> personMapper.fromRepository(person) }
    }

    override fun create(person: Person): Person {
        logger.info("Creating person.")

        val personEntity = personMapper.toRepository(person)

        val savedPerson = peopleRepository.save(personEntity)

        logger.info("Saved person successfully.")

        return personMapper.fromRepository(savedPerson)
    }

    override fun update(person: Person) {
        logger.info("Updating person.")

        val actualPerson = findById(person.id)

        actualPerson.firstName = person.firstName
        actualPerson.lastName = person.lastName
        actualPerson.address = person.address
        actualPerson.gender = person.gender

        val savingPerson = personMapper.toRepository(actualPerson)

        peopleRepository.save(savingPerson)

        logger.info("Person ${actualPerson.id} updated successfully.")
    }

    override fun delete(id: Long) {
        logger.info("Deleting person.")

        val actualPerson = findById(id)

        peopleRepository.deleteById(actualPerson.id)

        logger.info("Person $id deleted successfully.")
    }
}