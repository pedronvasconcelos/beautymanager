package com.pedrovasconcelos.beautymanager.infra.data.entities

data class EmployeeEmbeddedDocument(
    val id: String,
    val name: String,
    val role: String,
    val active: Boolean,
    val centerId: String
)

data class CustomerEmbedded(
    val id: String,
    val name: String,
    val centerId: String,
    val active: Boolean,
    val email: String?,
    val phone: String?
)