package br.com.edsontofolo.integrationtests.swagger

import br.com.edsontofolo.integrationtests.TestConfigurations.SERVER_PORT
import br.com.edsontofolo.integrationtests.testcontainers.AbstractIntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest :  AbstractIntegrationTest() {

    @Test
    fun shouldDisplaySwaggerUiPage() {
        val content = given()
            .basePath("/swagger-ui/index.html")
            .port(SERVER_PORT)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract().body()
            .asString()

        assertTrue(content.contains("Swagger UI"))
    }

}