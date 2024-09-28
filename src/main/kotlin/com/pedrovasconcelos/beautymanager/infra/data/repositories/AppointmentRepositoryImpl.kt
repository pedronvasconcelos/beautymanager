package com.pedrovasconcelos.beautymanager.infra.data.repositories

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.scheduler.Appointment
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentRepository
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.infra.data.entities.AppointmentDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class AppointmentRepositoryImpl  : AppointmentRepository {
    override fun saveAppointment(appointment: Appointment): Either<RepositoryError, Appointment> {
        TODO("Not yet implemented")
    }

    override fun checkAvailability(centerId: UUID, employeeId: UUID, start: LocalDateTime, duration: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCenterAppointments(centerId: UUID): Either<RepositoryError, List<Appointment>> {
        TODO("Not yet implemented")
    }

}

interface AppointmentMongoRepository : MongoRepository<AppointmentDocument, String>
