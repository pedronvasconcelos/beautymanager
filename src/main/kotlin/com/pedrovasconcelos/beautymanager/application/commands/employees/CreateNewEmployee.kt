package com.pedrovasconcelos.beautymanager.application.commands.employees

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import com.pedrovasconcelos.beautymanager.domain.centers.addEmployee
import com.pedrovasconcelos.beautymanager.domain.centers.createEmployee
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class CreateNewEmployee(private val centerRepository: CenterRepository) {


    fun execute(command: CreateNewEmployeeCommand): Either<BaseError, CreateNewEmployeeResponse> =
        validateInput(command)
            .flatMap { createEmployee(it.name, it.role, it.centerId) }
            .flatMap { saveEmployee(it) }
            .map { CreateNewEmployeeResponse(it.id.toString(), it.name, it.role) }

    private fun validateInput(command: CreateNewEmployeeCommand): Either<BaseError, CreateNewEmployeeCommand> {
        return if (command.name.isBlank() || command.role.isBlank()) {
            ValidationError("Name and role must not be blank").left()
        } else {
            command.right()
        }
    }


    private fun saveEmployee(employee: Employee): Either<BaseError, Employee> =
        centerRepository.getCenter(employee.centerId)
            .flatMap { center ->
               center.addEmployee(employee)
                    .fold(
                        ifLeft = { return it.left() },
                        ifRight = { centerRepository.saveCenter(it) }
                    )
            }
            .map { employee }


}


data class CreateNewEmployeeCommand(
    val name: String,
    val centerId: UUID,
    val role: String
)

data class CreateNewEmployeeResponse(
    val id: String,
    val name: String,
    val role: String
) {
    companion object
}