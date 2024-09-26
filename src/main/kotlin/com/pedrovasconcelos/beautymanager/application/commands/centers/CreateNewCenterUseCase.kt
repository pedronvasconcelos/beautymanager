package com.pedrovasconcelos.beautymanager.application.commands.centers

import arrow.core.Either
import arrow.core.flatMap
import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.centers.createCenter
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import org.springframework.stereotype.Service

@Service
class CreateNewCenterUseCase(private val centerRepository: CenterRepository) {

    fun execute(createNewCenterCommand: CreateNewCenterCommand): Either<BaseError, Center> =
        createCenter(createNewCenterCommand.name, createNewCenterCommand.email).flatMap { center ->
            centerRepository.saveCenter(center)
        }
}



data class CreateNewCenterCommand(
    val name: String,
    val email: String)