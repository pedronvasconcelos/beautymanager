package com.pedrovasconcelos.beautymanager.application.commands.scheduler

import arrow.core.*
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentRepository
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentStatus
import com.pedrovasconcelos.beautymanager.domain.scheduler.createAppointment
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.BusinessError
import com.pedrovasconcelos.beautymanager.domain.shared.NotFoundError
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class SchedulerAppointmentHandler(private val appointmentRepository: AppointmentRepository,
                                  private val centerRepository: CenterRepository) {

    fun handler(command: CreateAppointment): Either<BaseError, CreateAppointmentResponse> {

        val centerResult = centerRepository.getCenter(command.centerId)

        val center = centerResult.getOrNull() ?: return Either.Left(NotFoundError("Center not found"))

        val employee = center.employees.find { it.id == command.employeeId }
            ?: return Either.Left(NotFoundError("Employee not found"))

        val customer = center.customers.find { it.id == command.customerId }
            ?: return Either.Left(NotFoundError("Customer not found"))

        val appointment = createAppointment(customer, employee, center.id, command.start, command.duration, command.service)

        val available = appointmentRepository.checkAvailability(appointment)
        if(!available) {
            return Either.Left(AppointmentNotAvailableError())
        }
        val savedAppointment = appointmentRepository.saveAppointment(appointment)
        return savedAppointment.map {
            CreateAppointmentResponse(it.id, it.start, it.end, it.status)
        }
    }

}

data class CreateAppointment (
    val centerId: UUID,
    val employeeId: UUID,
    val customerId: UUID,
    val start: LocalDateTime,
    val duration: Int,
    val service: String?,
)

data class CreateAppointmentResponse(
    val id: UUID,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val status: AppointmentStatus
)

class AppointmentNotAvailableError : BusinessError("Employee not available at the requested time")