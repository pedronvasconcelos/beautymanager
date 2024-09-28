package com.pedrovasconcelos.beautymanager.domain.scheduler

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import java.util.*

interface AppointmentRepository {
    fun saveAppointment(appointment: Appointment): Either<RepositoryError, Appointment>
    fun checkAvailability(appointment : Appointment):  Boolean
    fun getCenterAppointments(centerId: UUID): Either<RepositoryError,List<Appointment>>
    fun getAppointment(appointmentId: UUID): Either<RepositoryError, Appointment>
}