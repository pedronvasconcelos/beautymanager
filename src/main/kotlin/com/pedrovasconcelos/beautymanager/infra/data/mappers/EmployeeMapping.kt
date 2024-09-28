package com.pedrovasconcelos.beautymanager.infra.data.mappers

import CenterDocument
import EmployeeEmbeddedDocument
import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import java.util.*

fun Center.toDocument(): CenterDocument =
    CenterDocument(
        id = id.toString(),
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDocument() }
    )

fun CenterDocument.toDomain(): Center =
    Center(
        id = UUID.fromString(id),
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDomain() }
    )

fun Employee.toDocument(): EmployeeEmbeddedDocument =
    EmployeeEmbeddedDocument(
        id = id.toString(),
        name = name,
        role = role,
        active = active,
        centerId = centerId.toString()
    )

fun EmployeeEmbeddedDocument.toDomain(): Employee =
    Employee(
        id = UUID.fromString(id),
        name = name,
        role = role,
        active = active,
        centerId = UUID.fromString(centerId)
    )