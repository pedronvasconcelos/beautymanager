package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError
import org.springframework.http.ResponseEntity

abstract class BaseController {

    protected fun handleError(error: BaseError): ResponseEntity<Any> {
        return when (error) {
            is ValidationError -> ResponseEntity.badRequest().body(error.message)
            is RepositoryError -> ResponseEntity.status(500).body(error.message)
            else -> ResponseEntity.status(500).body("An unexpected error occurred.")
        }
    }
}
