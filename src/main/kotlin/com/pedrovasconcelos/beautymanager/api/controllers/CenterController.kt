package com.pedrovasconcelos.beautymanager.application.controllers

import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterCommand
import com.pedrovasconcelos.beautymanager.application.commands.centers.CreateNewCenterUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/centers")
class CenterController(
    private val createNewCenterUseCase: CreateNewCenterUseCase
) : BaseController() {

    @PostMapping
    fun createCenter(@RequestBody command: CreateNewCenterCommand): ResponseEntity<Any> {
        val result = createNewCenterUseCase.execute(command)
        return result.fold(
            { error -> handleError(error) },
            { center -> ResponseEntity.ok(center) }
        )
    }
}
