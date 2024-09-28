package com.pedrovasconcelos.beautymanager.api.models

import com.pedrovasconcelos.beautymanager.domain.shared.BaseError

@Suppress("unused")
data class ApiError (val message: String) {
    companion object {
        fun from(message: String): ApiError {
            return ApiError(message)
        }
        fun from(baseError : BaseError) : ApiError {
            return ApiError(baseError.message)
        }
    }
}