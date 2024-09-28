package com.pedrovasconcelos.beautymanager.infra.data.entities

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "appointments")
data class AppointmentDocument (val id : String,
                                val centerId : String,
                                val employee : EmployeeEmbeddedDocument,
                                val customer : CustomerEmbedded,
                                val start : LocalDateTime,
                                val end : LocalDateTime,
                                val service : String?,
                                val notes : String?,
                                val status : String,
)