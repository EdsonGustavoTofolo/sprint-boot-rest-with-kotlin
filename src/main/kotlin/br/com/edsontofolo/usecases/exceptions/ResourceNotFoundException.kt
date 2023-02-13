package br.com.edsontofolo.usecases.exceptions

class ResourceNotFoundException(exception: String?): RuntimeException(exception) {
}