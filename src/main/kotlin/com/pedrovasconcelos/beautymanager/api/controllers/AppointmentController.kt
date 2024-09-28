package com.pedrovasconcelos.beautymanager.api.controllers

import com.pedrovasconcelos.beautymanager.application.commands.scheduler.CreateAppointment
import com.pedrovasconcelos.beautymanager.application.commands.scheduler.SchedulerAppointmentHandler
import com.pedrovasconcelos.beautymanager.application.controllers.BaseController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    private val appointmentHandler: SchedulerAppointmentHandler
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
}