package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.api.models.ApiError.Companion.from
import com.pedrovasconcelos.beautymanager.domain.shared.*
import org.springframework.http.ResponseEntity

abstract class BaseController {

    protected fun handleError(error: BaseError): ResponseEntity<Any> {
        return when (error) {
            is ValidationError -> ResponseEntity.badRequest().body(from(error))
            is BusinessError -> ResponseEntity.badRequest().body(from(error))
            is RepositoryError -> ResponseEntity.status(500).body(from(error))
            is NotFoundError -> ResponseEntity.notFound().build()
            is NoContentResponse -> ResponseEntity.noContent().build()
            else -> ResponseEntity.status(500).body(from("An unexpected error occurred."))
        }
    }
}
