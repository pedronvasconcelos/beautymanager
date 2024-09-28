package com.pedrovasconcelos.beautymanager.domain.customers

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.*
import com.pedrovasconcelos.beautymanager.domain.shared.EmailAddress.Companion.createEmail
import com.pedrovasconcelos.beautymanager.domain.shared.PhoneNumber.Companion.createPhoneNumber

import java.util.*

data class Customer(
    val id: UUID,
    val name: String,
    val centerId: UUID,
    val active: Boolean,
    val email: EmailAddress?,
    val phone: PhoneNumber?
)

fun createCustomer(name: String, centerId: UUID, email: String?, phone: String?): Either<BaseError, Customer> {
    if (name.isBlank()) {
        return Either.Left(ValidationError("Invalid name"))
    }
    try {
        val emailAddress = createEmail(email)

        return Either.Right(Customer(UUID.randomUUID(), name, centerId, true, emailAddress, createPhoneNumber(phone)))
    } catch (e: ValidationException) {
        return Either.Left(ValidationError(e.message))
    }

}

fun updateEmail(customer: Customer, emailAddress: String): Either<BaseError, Customer> {
    try {
        val email = createEmail(emailAddress)
        return Either.Right(customer.copy(email = email))
    } catch (e: ValidationException) {
        return Either.Left(ValidationError(e.message))
    }
}

fun updatePhone(customer: Customer, phone: String): Either<BaseError, Customer> {
    try {
        return Either.Right(customer.copy(phone = createPhoneNumber(phone)))
    } catch (e: ValidationException) {
        return Either.Left(ValidationError(e.message))
    }
}
