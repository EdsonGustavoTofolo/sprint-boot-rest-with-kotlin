package br.com.edsontofolo.integrationtests

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

object TestConfigurations {
    const val SERVER_PORT = 8888

    const val HEADER_PARAM_ORIGIN = HttpHeaders.ORIGIN
    const val CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_VALUE

    const val ORIGIN_EDSON = "https://edson.com.br"
    const val ORIGIN_LOCALHOST = "http://localhost:8080"
    const val ORIGIN_OTHER = "https://other.com.br"
}