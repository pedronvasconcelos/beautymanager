package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterCommand
import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterUseCase
import com.pedrovasconcelos.beautymanager.application.commands.employees.CreateNewEmployee
import com.pedrovasconcelos.beautymanager.application.commands.employees.CreateNewEmployeeCommand
import com.pedrovasconcelos.beautymanager.application.queries.centers.GetCenterDetailsHandler
import com.pedrovasconcelos.beautymanager.application.queries.centers.GetCenterEmployeesHandler
import com.pedrovasconcelos.beautymanager.application.queries.centers.GetCenterEmployeesQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/centers")
class CenterController(
    private val createNewCenterUseCase: CreateNewCenterUseCase,
    private val createNewEmployeeUseCase: CreateNewEmployee,
    private val getCenterDetailsHandler: GetCenterDetailsHandler,
    private val getCenterEmployeesHandler: GetCenterEmployeesHandler
) : BaseController() {

    @PostMapping
    fun createCenter(@RequestBody command: CreateNewCenterCommand): ResponseEntity<Any> {
        return createNewCenterUseCase.execute(command)
            .fold(
                { error -> handleError(error) },
                { center -> ResponseEntity.ok(center) }
            )
    }

    @PostMapping("/employees")
    fun createEmployee(@RequestBody command: CreateNewEmployeeCommand): ResponseEntity<Any> {
        return createNewEmployeeUseCase.execute(command)
            .fold(
                { error -> handleError(error) },
                { employee -> ResponseEntity.ok(employee) }
            )
    }

    @GetMapping("/{centerId}")
    fun getCenterDetails(@PathVariable centerId: String): ResponseEntity<Any> {
        return getCenterDetailsHandler.handle(centerId)
            .fold(
                { error -> handleError(error) },
                { center -> ResponseEntity.ok(center) }
            )
    }

    @GetMapping("/{centerId}/employees")
    fun getCenterEmployees(
        @PathVariable centerId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Any> {
        val query = GetCenterEmployeesQuery(
            centerId = centerId,
            page = page,
            size = size
        )
        return getCenterEmployeesHandler.handle(query)
            .fold(
                { error -> handleError(error) },
                { employees -> ResponseEntity.ok(employees) }
            )
    }

}
