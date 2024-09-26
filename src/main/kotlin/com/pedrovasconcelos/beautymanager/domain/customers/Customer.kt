package com.pedrovasconcelos.beautymanager.domain.customers

import java.util.*

data class Customer(val id: UUID,
                    val name: String,
                    val centerId: UUID,
                    val active: Boolean,
                    val email: String?,
                    val phone: String?)

fun createCustomer(name: String, centerId: UUID, email: String?, phone: String?) : Customer {
    return Customer(UUID.randomUUID(), name, centerId, true, email, phone)
}

fun updateEmail(customer: Customer, email: String) : Customer {
    return customer.copy(email = email)
}

fun updatePhone(customer: Customer, phone: String) : Customer {
    return customer.copy(phone = phone)
}