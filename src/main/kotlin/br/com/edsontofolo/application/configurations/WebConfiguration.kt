package br.com.edsontofolo.application.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(private val webServerConfiguration: WebServerConfiguration): WebMvcConfigurer {

    private val MEDIA_TYPE_APPLICATION_YAML = MediaType.valueOf("application/x-yaml")

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        /*
        Configuration to support query parameter named "mediaType".
        Example: localhost:8080/api/person/v1?mediaType=xml or ?mediaType=json

        configurer.favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)

         */

        /*
        Configuration to support header to content negotiation.
        Example: Add header "Accept": "application/xml" or "application/json"
         */
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML)
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(YamlJackson2HttpMessageConverter())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(*webServerConfiguration.originPatterns.toTypedArray())
    }
}