package br.com.edsontofolo.application.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "application")
data class ApplicationConfiguration(
    var name: String = "",
    var description: String = "",
    var version: String = "",
)