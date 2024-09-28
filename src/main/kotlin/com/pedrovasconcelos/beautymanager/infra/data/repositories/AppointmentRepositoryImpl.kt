package com.pedrovasconcelos.beautymanager.infra.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.scheduler.Appointment
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentRepository
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.infra.data.entities.AppointmentDocument
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDocument
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDomain
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

import java.util.*

@Repository
class AppointmentRepositoryImpl(
    private val mongoRepository: AppointmentMongoRepository,
    private val mongoTemplate: MongoTemplate
) : AppointmentRepository {


    override fun saveAppointment(appointment: Appointment): Either<RepositoryError, Appointment> =
        runCatching {
            mongoRepository.save(appointment.toDocument())
        }.mapCatching {
            it.toDomain()
        }.fold(
            onSuccess = { it.right() },
            onFailure = { RepositoryError().left() }
        )

    override fun checkAvailability(appointment : Appointment): Boolean {
        val query = Query().addCriteria(
            Criteria.where("centerId").`is`(appointment.centerId.toString())
                .and("employee.id").`is`(appointment.employee.id.toString())
                .andOperator(
                    Criteria.where("start").lt(appointment.end),
                    Criteria.where("end").gt(appointment.start)
                ))


        val conflictingAppointments = mongoTemplate.find(query, AppointmentDocument::class.java)
        return conflictingAppointments.isEmpty()
    }

    override fun getCenterAppointments(centerId: UUID): Either<RepositoryError, List<Appointment>> =
        runCatching {
            val query = Query(Criteria.where("centerId").`is`(centerId.toString()))
            mongoTemplate.find(query, AppointmentDocument::class.java)
        }.mapCatching { documents ->
            documents.map { it.toDomain() }
        }.fold(
            onSuccess = { it.right() },
            onFailure = { RepositoryError().left() }
        )

    override fun getAppointment(appointmentId: UUID): Either<RepositoryError, Appointment> =
          runCatching { mongoRepository.findById(appointmentId.toString())}
            .mapCatching { it.orElse(null)?.toDomain() }
            .fold(
                onSuccess = { it?.right() ?: RepositoryError().left() },
                onFailure = { RepositoryError().left() }
            )


}

interface AppointmentMongoRepository : MongoRepository<AppointmentDocument, String>
