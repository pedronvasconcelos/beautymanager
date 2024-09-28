package com.pedrovasconcelos.beautymanager.application.commands.scheduler

import arrow.core.Either
import arrow.core.left
import com.pedrovasconcelos.beautymanager.domain.scheduler.Appointment
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentRepository
import com.pedrovasconcelos.beautymanager.domain.scheduler.completeAppointment
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.NotFoundError
import org.springframework.stereotype.Service
import java.util.UUID

data class CompleteAppointment(
    val appointmentId: UUID
)
@Service
class CompleteAppointmentHandler(private val appointmentRepository: AppointmentRepository) {
    fun handler(request: CompleteAppointment): Either<BaseError, Appointment> {
        val appointment = appointmentRepository.getAppointment(request.appointmentId)
            .mapLeft { return Either.Left(it) }
            .getOrNull() ?: return NotFoundError("Appointment not found").left()

        val completedAppointment = completeAppointment(appointment)
        return appointmentRepository.saveAppointment(completedAppointment)
            .map { completedAppointment }
            .mapLeft { return Either.Left(it) }
    }
}