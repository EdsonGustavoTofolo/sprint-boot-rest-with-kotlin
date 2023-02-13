package br.com.edsontofolo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StartupApplication

fun main(args: Array<String>) {
    runApplication<StartupApplication>(*args)
}
