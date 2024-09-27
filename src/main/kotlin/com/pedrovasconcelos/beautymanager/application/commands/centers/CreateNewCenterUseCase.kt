package com.pedrovasconcelos.beautymanager.application.commands.centers

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.centers.createCenter
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError
import org.springframework.stereotype.Service

@Service
class CreateNewCenterUseCase(private val centerRepository: CenterRepository) {

    fun execute(createNewCenterCommand: CreateNewCenterCommand): Either<BaseError, CreateNewCenterResponse> =
        validateInput(createNewCenterCommand)
            .flatMap { createCenter(it.name, it.email) }
            .flatMap(centerRepository::saveCenter)
            .map { CreateNewCenterResponse(it.id.toString(), it.name, it.email) }


    private fun validateInput(command: CreateNewCenterCommand): Either<BaseError, CreateNewCenterCommand> {
        return if (command.name.isBlank() || command.email.isBlank()) {
            ValidationError("Name and email must not be blank").left()
        } else {
            command.right()
        }
    }
}



data class CreateNewCenterCommand(
    val name: String,
    val email: String)


data class CreateNewCenterResponse(
    val id: String,
    val name: String,
    val email: String
) {
    companion object
}