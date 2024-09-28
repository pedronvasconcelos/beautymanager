package com.pedrovasconcelos.beautymanager.infra.data.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "appointments")
data class AppointmentDocument (val id : String,
                                val centerId : String,
                                val employee : EmployeeEmbeddedDocument,
                                val customer : CustomerEmbedded,
                                val start : String,
                                val estimatedDuration : Int,
                                val service : String?,
                                val notes : String?,
                                val status : String,
)