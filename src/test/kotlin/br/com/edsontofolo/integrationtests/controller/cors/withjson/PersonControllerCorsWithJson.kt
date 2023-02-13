package br.com.edsontofolo.integrationtests.controller.cors.withjson

import br.com.edsontofolo.integrationtests.TestConfigurations
import br.com.edsontofolo.integrationtests.controller.cors.withjson.PersonControllerCorsWithJson.Constants.INVALID_CORS_REQUEST
import br.com.edsontofolo.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.edsontofolo.interfaceadapters.controllers.v1.dtos.PersonHttp
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.*
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson :  AbstractIntegrationTest() {

    object Constants {
        const val INVALID_CORS_REQUEST = "Invalid CORS request"
    }

    private val objectMapper: ObjectMapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    private lateinit var specification: RequestSpecification
    private lateinit var person: PersonHttp

    @BeforeAll
    fun setup() {
        person = PersonHttp()
    }

    @Test
    @Order(1)
    fun testCreate() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(TestConfigurations.HEADER_PARAM_ORIGIN, TestConfigurations.ORIGIN_EDSON)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigurations.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigurations.CONTENT_TYPE_JSON)
            .body(person)
            .post()
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .asString()

        person = objectMapper.readValue(content, PersonHttp::class.java)

        Assertions.assertNotNull(person)
        Assertions.assertTrue(person.code > 0)
        Assertions.assertEquals("John", person.firstName)
        Assertions.assertEquals("Dare", person.lastName)
        Assertions.assertEquals("Miami", person.address)
        Assertions.assertEquals("Male", person.gender)
    }

    @Test
    @Order(2)
    fun testCreateWithWrongOrigin() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(TestConfigurations.HEADER_PARAM_ORIGIN, TestConfigurations.ORIGIN_OTHER)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigurations.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigurations.CONTENT_TYPE_JSON)
            .body(person)
            .post()
            .then()
            .statusCode(HttpStatus.FORBIDDEN.value())
            .extract()
            .body()
            .asString()

        Assertions.assertEquals(INVALID_CORS_REQUEST, content)
    }

    @Test
    @Order(3)
    fun testFindById() {
        specification = RequestSpecBuilder()
            .addHeader(TestConfigurations.HEADER_PARAM_ORIGIN, TestConfigurations.ORIGIN_LOCALHOST)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigurations.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigurations.CONTENT_TYPE_JSON)
            .pathParam("id", person.code)
            .`when`()["{id}"]
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(content, PersonHttp::class.java)

        Assertions.assertNotNull(createdPerson)
        Assertions.assertTrue(createdPerson.code > 0)
        Assertions.assertEquals("John", createdPerson.firstName)
        Assertions.assertEquals("Dare", createdPerson.lastName)
        Assertions.assertEquals("Miami", createdPerson.address)
        Assertions.assertEquals("Male", createdPerson.gender)
    }

    @Test
    @Order(4)
    fun testFindByIdWithWrongOrigin() {
        specification = RequestSpecBuilder()
            .addHeader(TestConfigurations.HEADER_PARAM_ORIGIN, TestConfigurations.ORIGIN_OTHER)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigurations.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigurations.CONTENT_TYPE_JSON)
            .pathParam("id", person.code)
            .`when`()["{id}"]
            .then()
            .statusCode(HttpStatus.FORBIDDEN.value())
            .extract()
            .body()
            .asString()

        Assertions.assertEquals(INVALID_CORS_REQUEST, content)
    }

    private fun mockPerson() {
        person.firstName = "John"
        person.lastName = "Dare"
        person.address = "Miami"
        person.gender = "Male"
    }
}