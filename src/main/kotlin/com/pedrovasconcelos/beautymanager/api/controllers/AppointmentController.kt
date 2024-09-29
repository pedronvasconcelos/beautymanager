package com.pedrovasconcelos.beautymanager.api.controllers

import com.pedrovasconcelos.beautymanager.application.commands.scheduler.CompleteAppointment
import com.pedrovasconcelos.beautymanager.application.commands.scheduler.CompleteAppointmentHandler
import com.pedrovasconcelos.beautymanager.application.commands.scheduler.CreateAppointment
import com.pedrovasconcelos.beautymanager.application.commands.scheduler.SchedulerAppointmentHandler
import com.pedrovasconcelos.beautymanager.application.controllers.BaseController
import com.pedrovasconcelos.beautymanager.application.queries.appointments.ListAppointmentsByCenterHandler
import com.pedrovasconcelos.beautymanager.application.queries.appointments.ListAppointmentsByCenterQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    private val appointmentHandler: SchedulerAppointmentHandler,
    private val completeAppointmentHandler: CompleteAppointmentHandler,
    private val listAppointmentsByCenterQuery: ListAppointmentsByCenterHandler,
) : BaseController() {

    @PostMapping
    fun createNewAppoiment(@RequestBody request: CreateAppointment): ResponseEntity<Any> {
        return appointmentHandler.handler(
            request
        ).fold(
            { error -> handleError(error) },
            { customer -> ResponseEntity.ok(customer) }
        )
    }

    @PatchMapping("/{appointmentId}/complete")
    fun updateAppointment(@RequestBody request: CompleteAppointment): ResponseEntity<Any> {
        return completeAppointmentHandler.handler(
            request
        ).fold(
            { error -> handleError(error) },
            { customer -> ResponseEntity.ok(customer) }
        )
    }

    @PatchMapping("/{appointmentId}/cancel")
    fun cancelAppointment(@RequestBody request: CompleteAppointment): ResponseEntity<Any> {
        return completeAppointmentHandler.cancelAppointment(
            request
        ).fold(
            { error -> handleError(error) },
            { customer -> ResponseEntity.ok(customer) }
        )
    }

    @GetMapping
    fun getAppointments(
        @RequestParam(required = true) centerId : UUID,
        @RequestParam(required = true) day: String,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) employeeId: UUID?,
        @RequestParam(required = false) customerId: UUID?,
        @RequestParam(required = false) service: String?,
        @RequestParam(required = false) employeeName: String?,
        @RequestParam(required = false) customerName: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ) : ResponseEntity<Any>{


        val query = ListAppointmentsByCenterQuery(
            centerId = centerId,
            day = day,
            status = status,
            employeeId = employeeId,
            customerId = customerId,
            service = service,
            employeeName = employeeName,
            customerName = customerName,
            page = page,
            size = size
        )
        return listAppointmentsByCenterQuery.handler(query)
            .fold(
                { error -> handleError(error) },
                { appointments -> ResponseEntity.ok(appointments) }
            )
    }

}