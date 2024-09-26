package com.pedrovasconcelos.beautymanager.domain.scheduler

import java.time.LocalDateTime
import java.util.*

data class Appointment(val id: UUID,
                       val customerId: UUID,
                       val employeeId: UUID,
                       val centerId: UUID,
                       val start: LocalDateTime,
                       val end: LocalDateTime,
                       val service: String?,
                       val notes: String?,
                       val status: AppointmentStatus)

fun createAppointment(customerId: UUID, employeeId: UUID, centerId: UUID, start: LocalDateTime, end: LocalDateTime, service: String?) : Appointment {
    return Appointment(UUID.randomUUID(), customerId, employeeId, centerId, start, end, service, null, AppointmentStatus.SCHEDULED)
}

fun completeAppointment(appointment: Appointment) : Appointment {
    return appointment.copy(status = AppointmentStatus.COMPLETED)
}


fun updateNotes(appointment: Appointment, notes: String) : Appointment {
    return appointment.copy(notes = notes)
}

fun cancelAppointment(appointment: Appointment) : Appointment {
    return appointment.copy(status = AppointmentStatus.CANCELLED)
}

fun updateAppointment(appointment: Appointment, start: LocalDateTime, end: LocalDateTime, service: String?) : Appointment {
    return appointment.copy(start = start, end = end, service = service)
}

fun updateEmployee(appointment: Appointment, employeeId: UUID) : Appointment {
    return appointment.copy(employeeId = employeeId)
}
fun updateCustomer(appointment: Appointment, customerId: UUID) : Appointment {
    return appointment.copy(customerId = customerId)
}
enum class AppointmentStatus{
    SCHEDULED,
    CANCELLED,
    COMPLETED
}
