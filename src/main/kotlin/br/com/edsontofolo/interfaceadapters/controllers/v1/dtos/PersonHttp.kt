package br.com.edsontofolo.interfaceadapters.controllers.v1.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.hateoas.RepresentationModel

/*
    Propriedade necessaria pois com application/x-yaml o campo ID vem na ordem errada
 */
@JsonPropertyOrder("id", "firstName", "lastName", "address", "gender")
/*
    Para consumir com application/x-yaml, a classe não pode ser imutavel
 */
data class PersonHttp(
    @field:JsonProperty("id")
    var code: Long = 0,
    @field:JsonProperty("first_name")
    var firstName: String = "",
    @field:JsonProperty("last_name")
    var lastName: String = "",
    var address: String = "",
    var gender: String = ""
): RepresentationModel<PersonHttp>()