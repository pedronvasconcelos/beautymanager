package com.pedrovasconcelos.beautymanager.infra.data.mappers

import CenterDocument

import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import com.pedrovasconcelos.beautymanager.domain.customers.Customer
import com.pedrovasconcelos.beautymanager.domain.shared.EmailAddress
import com.pedrovasconcelos.beautymanager.domain.shared.PhoneNumber
import com.pedrovasconcelos.beautymanager.infra.data.entities.CustomerEmbedded
import com.pedrovasconcelos.beautymanager.infra.data.entities.EmployeeEmbeddedDocument
import java.util.*

fun Center.toDocument(): CenterDocument =
    CenterDocument(
        id = id.toString(),
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDocument() },
        customers = customers.map { it.toDocument() }
    )

fun CenterDocument.toDomain(): Center =
    Center(
        id = UUID.fromString(id),
        name = name,
        email = email,
        active = active,
        employees = employees.map { it.toDomain() },
        customers = customers.map { it.toDomain() }
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

fun Customer.toDocument(): CustomerEmbedded =
    CustomerEmbedded(
        id = id.toString(),
        name = name,
        centerId = centerId.toString(),
        active = active,
        email = email?.value,
        phone = phone?.value
    )

fun CustomerEmbedded.toDomain(): Customer =
    Customer(
        id = UUID.fromString(id),
        name = name,
        centerId = UUID.fromString(centerId),
        active = active,
        email = email?.let { EmailAddress(it) },
        phone = phone?.let { PhoneNumber(it) }
    )
