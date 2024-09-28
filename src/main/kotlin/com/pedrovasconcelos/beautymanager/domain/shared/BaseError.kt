package com.pedrovasconcelos.beautymanager.domain.shared

import arrow.core.Either
import arrow.core.left
import arrow.core.right

abstract  class BaseError {
    open val message: String = "Error"
}

data class ValidationError(override val message : String) : BaseError() {
}

data class NotFoundError(override val message : String) : BaseError() {
}

data class NoContentResponse(override val message : String) : BaseError() {
}
open class BusinessError(override val message : String) : BaseError() {
}
fun <T> T?.toEither(error: BaseError): Either<BaseError, T> =
    this?.right() ?: error.left()

fun <T> List<T>.toEitherIfNotEmpty(error: BaseError): Either<BaseError, List<T>> =
    if (this.isNotEmpty()) this.right() else error.left()


class PaginatedResponse<T>(
    val data: List<T>,
    val page: Int,
    val size: Int,
    val total: Int
) {
    val totalPages: Int = if (size == 0) 0 else (total + size - 1) / size
}