package br.com.edsontofolo.application.configurations

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration(private val applicationConfiguration: ApplicationConfiguration) {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title(applicationConfiguration.name)
                    .description(applicationConfiguration.description)
                    .version(applicationConfiguration.version)
                    .termsOfService("http://github.io/edsontofolo/termos")
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("http://github.io/edsontofolo/license")
                    )
            )
    }
}