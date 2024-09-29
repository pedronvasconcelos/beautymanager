package com.pedrovasconcelos.beautymanager.infra.data.mappers

import com.pedrovasconcelos.beautymanager.domain.scheduler.Appointment
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentStatus
import com.pedrovasconcelos.beautymanager.infra.data.entities.AppointmentDocument

fun Appointment.toDocument(): AppointmentDocument =
    AppointmentDocument(
        id = id.toString(),
        customer = customer.toDocument(),
        employee = employee.toDocument(),
        centerId = centerId.toString(),
        start = start,
        end= end,
        service = service,
        notes = notes,
        status = status.name,
        price = price
    )

fun AppointmentDocument.toDomain(): Appointment =
    Appointment(
        id = java.util.UUID.fromString(id),
        customer = customer.toDomain(),
        employee = employee.toDomain(),
        centerId = java.util.UUID.fromString(centerId),
        start = start,
        end = end,
        service = service,
        notes = notes,
        status = AppointmentStatus.valueOf(status)
    )