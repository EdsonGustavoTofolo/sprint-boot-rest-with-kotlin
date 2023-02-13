package br.com.edsontofolo.interfaceadapters.controllers

import br.com.edsontofolo.interfaceadapters.controllers.utils.MediaType.APPLICATION_YAML_VALUE
import br.com.edsontofolo.interfaceadapters.controllers.v1.dtos.PersonHttp
import br.com.edsontofolo.interfaceadapters.controllers.v1.mappers.PersonMapper
import br.com.edsontofolo.usecases.interactors.PersonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.mapstruct.factory.Mappers
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("ApplicationControllerV1")
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "API for managing people")
class ApplicationController(private val personService: PersonService) {

    private val personMapper: PersonMapper = Mappers.getMapper(PersonMapper::class.java)

    @Operation(summary = "Finds a person", description = "Find one person by ID",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonHttp::class))
                ]),
            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE])
    fun findById(@PathVariable id: Long): PersonHttp {
        val person = personService.findById(id)

        val personResponse = personMapper.fromDomain(person)

        val withSelfRel = WebMvcLinkBuilder.linkTo(ApplicationController::class.java).slash(personResponse.code).withSelfRel()
        personResponse.add(withSelfRel)

        return personResponse
    }

    @Operation(summary = "Finds all people", description = "Find all people",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = PersonHttp::class)))
                ]),
            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE])
    fun findAll(): List<PersonHttp> {
         return personService.findAll()
            .map {
                val personResponse = personMapper.fromDomain(it)

                val withSelfRel = WebMvcLinkBuilder.linkTo(ApplicationController::class.java).slash(personResponse.code).withSelfRel()
                personResponse.add(withSelfRel)

                personResponse
            }
    }

    @Operation(summary = "Adds a new person", description = "Adds a new person",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Created",
                responseCode = "201",
                content = [
                    Content(schema = Schema(implementation = PersonHttp::class))
                ]),
            ApiResponse(
                description = "Bad request",
                responseCode = "400",
                content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody personHttp: PersonHttp?): PersonHttp {
        val person = personMapper.toDomain(personHttp ?: throw IllegalArgumentException("Person is required"))

        val createdPerson = personService.create(person)

         val personResponse = personMapper.fromDomain(createdPerson)

        val withSelfRel = WebMvcLinkBuilder.linkTo(ApplicationController::class.java).slash(personResponse.code).withSelfRel()
        personResponse.add(withSelfRel)

        return personResponse
    }

    @Operation(summary = "Updates a person", description = "Updates a person",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonHttp::class))
                ]),
            ApiResponse(
                description = "Bad request",
                responseCode = "400",
                content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody personHttp: PersonHttp?) {
        val personResponse = personMapper.toDomain(personHttp ?: throw IllegalArgumentException("Person is required"))

        personService.update(personResponse)
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        personService.delete(id)
    }
}