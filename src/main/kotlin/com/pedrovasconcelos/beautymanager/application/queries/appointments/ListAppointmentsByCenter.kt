package com.pedrovasconcelos.beautymanager.application.queries.appointments

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.application.queries.shared.PaginatedResponse
import com.pedrovasconcelos.beautymanager.domain.scheduler.Appointment
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.infra.data.entities.AppointmentDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class ListAppointmentsByCenterHandler(private val mongoTemplate: MongoTemplate){

    fun handler(query: ListAppointmentsByCenterQuery): Either<BaseError, PaginatedResponse<AppointmentResponse>> {
      try{
        return performQuery(query).right()
      } catch (e: Exception) {
        return  RepositoryError().left()
      }

    }
    private fun performQuery(query: ListAppointmentsByCenterQuery) : PaginatedResponse<AppointmentResponse>
    {
        val mongoQuery = Query(Criteria.where("centerId").`is`(query.centerId.toString()))
            .addCriteria(createDateCriteria(LocalDate.parse(query.day)))
        if(query.status != null) {
            mongoQuery.addCriteria(Criteria.where("status").`is`(query.status))
        }
        if(query.employeeId != null) {
            mongoQuery.addCriteria(Criteria.where("employeeId").`is`(query.employeeId))
        }
        if(query.customerId != null) {
            mongoQuery.addCriteria(Criteria.where("customerId").`is`(query.customerId))
        }
        if(query.service != null) {
            mongoQuery.addCriteria(Criteria.where("service").`is`(query.service))
        }
        if(query.employeeName != null) {
            mongoQuery.addCriteria(Criteria.where("employeeName").`is`(query.employeeName))
        }
        if(query.customerName != null) {
            mongoQuery.addCriteria(Criteria.where("customerName").`is`(query.customerName))
        }

        mongoQuery.fields().include("id").include("centerId").include("employee").include("employee")
            .include("customer").include("customer").include("start").include("end").include("status").include("service")
        val skip = (query.page -1)* query.size
        mongoQuery.skip(skip.toLong()).limit(query.size)

        val appointments = mongoTemplate.find(mongoQuery, AppointmentDocument::class.java)
        val total = mongoTemplate.count(mongoQuery, AppointmentDocument::class.java).toInt()
        val appointmentResponses = appointments.map { it.toAppointmentResponse() }
        return PaginatedResponse(appointmentResponses, query.page, query.size, total)
    }

    fun createDateCriteria(queryDay: LocalDate): Criteria {
        val startOfDay: Date = Date.from(queryDay.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val endOfDay: Date = Date.from(queryDay.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())

        return Criteria.where("start").gte(startOfDay).lt(endOfDay)
    }
}

data class ListAppointmentsByCenterQuery(
    val centerId: UUID,
    val day: String,
    val status: String?,
    val employeeId: UUID?,
    val customerId: UUID?,
    val service: String?,
    val employeeName: String?,
    val customerName: String?,
    val page: Int,
    val size: Int
)


data class AppointmentResponse(
    val id: String,
    val centerId: String,
    val employeeId: String,
    val employeeName: String,
    val customerId: String,
    val customerName: String,
    val start: String,
    val end: String,
    val status: String,
    val service: String?,
    val price: BigDecimal
)

fun AppointmentDocument.toAppointmentResponse() = AppointmentResponse(
    id = this.id,
    centerId = this.centerId,
    employeeId = this.employee.id,
    employeeName = this.employee.name,
    customerId = this.customer.id,
    customerName = this.customer.name,
    start = this.start.toString(),
    end = this.end.toString(),
    status = this.status,
    service = this.service,
    price = this.price
)

 fun LocalDateTime.formatDate(): String {
     val pattern: String = "yyyy-MM-dd HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}