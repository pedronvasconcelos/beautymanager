package com.pedrovasconcelos.beautymanager.domain.scheduler

import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import com.pedrovasconcelos.beautymanager.domain.customers.Customer
import java.time.LocalDateTime
import java.util.*

data class Appointment(val id: UUID,
                       val customer: Customer,
                       val employee: Employee,
                       val centerId: UUID,
                       val start: LocalDateTime,
                       val estimatedDuration: Int,
                       val service: String?,
                       val notes: String?,
                       val status: AppointmentStatus)

fun createAppointment(customer: Customer, employee: Employee, centerId: UUID, start: LocalDateTime, duration: Int, service: String?) : Appointment {
    return Appointment(UUID.randomUUID(), customer, employee, centerId, start, duration, service, null, AppointmentStatus.SCHEDULED)
}

fun completeAppointment(appointment: Appointment) : Appointment {
    return appointment.copy(status = AppointmentStatus.COMPLETED)
}


fun cancelAppointment(appointment: Appointment) : Appointment {
    return appointment.copy(status = AppointmentStatus.CANCELLED)
}



enum class AppointmentStatus{
    SCHEDULED,
    CANCELLED,
    COMPLETED
}
