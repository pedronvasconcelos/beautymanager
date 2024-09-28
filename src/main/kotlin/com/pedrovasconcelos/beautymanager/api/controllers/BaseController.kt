package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.domain.shared.*
import org.springframework.http.ResponseEntity

abstract class BaseController {

    protected fun handleError(error: BaseError): ResponseEntity<Any> {
        return when (error) {
            is ValidationError -> ResponseEntity.badRequest().body(error.message)
            is RepositoryError -> ResponseEntity.status(500).body(error.message)
            is NotFoundError -> ResponseEntity.notFound().build()
            is NoContentResponse -> ResponseEntity.noContent().build()
            else -> ResponseEntity.status(500).body("An unexpected error occurred.")
        }
    }
}
