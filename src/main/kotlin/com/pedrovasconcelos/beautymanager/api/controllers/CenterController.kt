package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterCommand
import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterUseCase
import com.pedrovasconcelos.beautymanager.application.commands.employees.CreateNewEmployee
import com.pedrovasconcelos.beautymanager.application.commands.employees.CreateNewEmployeeCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/centers")
class CenterController(
    private val createNewCenterUseCase: CreateNewCenterUseCase,
    private val createNewEmployeeUseCase: CreateNewEmployee,
) : BaseController() {

    @PostMapping
    fun createCenter(@RequestBody command: CreateNewCenterCommand): ResponseEntity<Any> {
        val result = createNewCenterUseCase.execute(command)
        return result.fold(
            { error -> handleError(error) },
            { center -> ResponseEntity.ok(center) }
        )
    }

    @PostMapping("/employees")
    fun createEmployee(@RequestBody command: CreateNewEmployeeCommand): ResponseEntity<Any> {
        val result = createNewEmployeeUseCase.execute(command)
        return result.fold(
            { error -> handleError(error) },
            { employee -> ResponseEntity.ok(employee) }
        )
    }

}
