// EmployeeRepositoryImpl.kt
package com.pedrovasconcelos.beautymanager.infra.data.repositories

import EmployeeEmbeddedDocument
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import com.pedrovasconcelos.beautymanager.domain.centers.EmployeeRepository
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDomain
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class EmployeeRepositoryImpl(
    private val employeeMongoRepository: EmployeeMongoRepository
) : EmployeeRepository {

    override fun saveEmployee(employee: Employee): Either<RepositoryError, Employee> =
        runCatching {
            employeeMongoRepository.save(employee.toDocument())
        }.mapCatching {
            it.toDomain()
        }.fold(
            onSuccess = { it.right() },
            onFailure = { RepositoryError().left() }
        )

    override fun getEmployee(id: UUID): Either<RepositoryError, Employee> =
        employeeMongoRepository.findById(id).orElse(null)?.let { document ->
            document.toDomain().right()
        } ?: RepositoryError.EmployeeNotFound.left()

    override fun getEmployeesByCenterId(centerId: UUID): Either<RepositoryError, List<Employee>> =
        runCatching {
            employeeMongoRepository.findByCenterId(centerId).map { it.toDomain() }
        }.fold(
            onSuccess = { employees ->
                if (employees.isNotEmpty()) {
                    employees.right()
                } else {
                    RepositoryError().left()
                }
            },
            onFailure = { RepositoryError().left() }
        )
}

interface EmployeeMongoRepository : MongoRepository<EmployeeEmbeddedDocument, UUID> {
    fun findByCenterId(centerId: UUID): List<EmployeeEmbeddedDocument>
}