package com.pedrovasconcelos.beautymanager.api.controllers

import com.pedrovasconcelos.beautymanager.application.commands.customers.CreateNewCustomer
import com.pedrovasconcelos.beautymanager.application.commands.customers.CreateNewCustomerHandler
import com.pedrovasconcelos.beautymanager.application.controllers.BaseController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")

class CustomerController(private val createNewCustomerHandler: CreateNewCustomerHandler) : BaseController() {

    @PostMapping
    fun createNewCustomer(@RequestBody request: CreateNewCustomer): ResponseEntity<Any> {
        return createNewCustomerHandler.handle(
           request
        ).fold(
            { error -> handleError(error) },
            { customer -> ResponseEntity.ok(customer) }
        )
    }
}