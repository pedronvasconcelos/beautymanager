package com.pedrovasconcelos.beautymanager.domain.centers

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError

import java.util.UUID

data class Employee(val id: UUID,
                    val name: String,
                    val role: String,
                    val active: Boolean,
                    val centerId: UUID ) {
    companion object
}


fun createEmployee(name: String, role: String, centerId: UUID): Either<EmployeeError, Employee> {
    return if (name.isBlank() || role.isBlank()) {
        Either.Left(EmployeeError.EmployeeNotValid)
    } else {
        Either.Right(Employee(UUID.randomUUID(), name, role, true, centerId))
    }
}



sealed class EmployeeError : BaseError() {
    object EmployeeNotValid : EmployeeError()
}

//error employer to return in either
