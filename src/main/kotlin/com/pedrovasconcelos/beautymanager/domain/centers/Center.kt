package com.pedrovasconcelos.beautymanager.domain.centers

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.customers.Customer
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError
import java.util.UUID

data class Center (val id : UUID,
                   val active : Boolean,
                   val name : String,
                   val email : String,
                   val employees : List<Employee> = emptyList(),
                   val customers : List<Customer> = emptyList()
    )

fun createCenter(name: String, email: String): Either<BaseError, Center> {
    return if (name.isBlank() || email.isBlank()) {
        ValidationError("Name and email must not be blank").left()
    } else {
        Center(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            active = true
        ).right()
    }
}

fun Center.addEmployee(employee: Employee): Either<BaseError, Center> =
    if (employees.none { it.name == employee.name })
        Right(copy(employees = employees + employee))
    else Left(
        ValidationError("Employee already exists")
    )

fun Center.addCustomer(customer: Customer): Either<BaseError, Center> =
    if (customers.none { it.name == customer.name })
        Right(copy(customers = customers + customer))
    else Left(
        ValidationError("Customer already exists")
    )