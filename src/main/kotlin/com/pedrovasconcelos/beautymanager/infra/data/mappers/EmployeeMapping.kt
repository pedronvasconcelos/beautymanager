package com.pedrovasconcelos.beautymanager.infra.data.mappers

import CenterDocument
import EmployeeEmbeddedDocument
import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.Employee

fun Center.toDocument(): CenterDocument =
    CenterDocument(
        id = id,
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDocument() }
    )

fun CenterDocument.toDomain(): Center =
    Center(
        id = id,
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDomain() }
    )

fun Employee.toDocument(): EmployeeEmbeddedDocument =
    EmployeeEmbeddedDocument(
        id = id,
        name = name,
        role = role,
        active = active,
        centerId = centerId
    )

fun EmployeeEmbeddedDocument.toDomain(): Employee =
    Employee(
        id = id,
        name = name,
        role = role,
        active = active,
        centerId = centerId
    )