package com.pedrovasconcelos.beautymanager.application.commands.customers

import arrow.core.Either
import arrow.core.raise.either
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.centers.addCustomer
import com.pedrovasconcelos.beautymanager.domain.customers.createCustomer
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class CreateNewCustomerHandler(private val centerRepository: CenterRepository) {

    fun handle(command: CreateNewCustomer): Either<BaseError, CreateNewCustomerResponse> = either {
        val center = centerRepository.getCenter(command.centerId).bind()
        val customer = createCustomer(command.name, command.centerId, command.email, command.phone)
            .mapLeft { it as BaseError }
            .bind()
        val updatedCenter = center.addCustomer(customer).bind()
        centerRepository.saveCenter(updatedCenter).bind()
        CreateNewCustomerResponse(
            id = customer.id.toString(),
            name = customer.name,
            centerId = customer.centerId.toString()
        )
    }
}

data class CreateNewCustomer(val name: String, val centerId: UUID, val email: String?, val phone: String?)

data class CreateNewCustomerResponse(val id: String, val name: String, val centerId: String)