package com.pedrovasconcelos.beautymanager.domain.shared

abstract  class BaseError {
    open val message: String = "Error"
}

data class ValidationError(override val message : String) : BaseError() {
}