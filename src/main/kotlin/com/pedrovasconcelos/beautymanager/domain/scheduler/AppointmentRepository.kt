package com.pedrovasconcelos.beautymanager.domain.scheduler

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import java.time.LocalDateTime
import java.util.*

interface AppointmentRepository {
    fun saveAppointment(appointment: Appointment): Either<RepositoryError, Appointment>
    fun checkAvailability(centerId: UUID, employeeId: UUID, start: LocalDateTime, duration: Int):  Boolean
}