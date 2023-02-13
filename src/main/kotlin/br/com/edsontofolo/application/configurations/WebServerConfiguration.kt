package br.com.edsontofolo.application.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cors")
data class WebServerConfiguration(
    val originPatterns: List<String> = ArrayList()
)