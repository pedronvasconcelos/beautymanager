package com.pedrovasconcelos.beautymanager.domain.centers

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import java.util.UUID

interface EmployeeRepository {
    fun saveEmployee(employee: Employee): Either<RepositoryError, Employee>
    fun getEmployee(id: UUID): Either<RepositoryError, Employee>
    fun getEmployeesByCenterId(centerId: UUID): Either<RepositoryError, List<Employee>>
}