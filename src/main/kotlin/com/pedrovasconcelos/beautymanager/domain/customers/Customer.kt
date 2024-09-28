package com.pedrovasconcelos.beautymanager.domain.customers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.EmailAddress
import com.pedrovasconcelos.beautymanager.domain.shared.EmailAddress.Companion.createEmail
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError

import java.util.*

data class Customer(val id: UUID,
                    val name: String,
                    val centerId: UUID,
                    val active: Boolean,
                    val email: EmailAddress?,
                    val phone: String?)

fun createCustomer(name: String, centerId: UUID, email: String?, phone: String?) : Either<BaseError, Customer> {
    if(name.isBlank()) {
        return Either.Left(ValidationError("Invalid name"))
    }

    return Either.Right(Customer(UUID.randomUUID(), name, centerId, true, createEmail(email), phone))
}

fun updateEmail(customer: Customer, emailAddress: String) : Customer {
    val email = createEmail(emailAddress)
    return customer.copy(email = email)
}

fun updatePhone(customer: Customer, phone: String) : Customer {
    return customer.copy(phone = phone)
}