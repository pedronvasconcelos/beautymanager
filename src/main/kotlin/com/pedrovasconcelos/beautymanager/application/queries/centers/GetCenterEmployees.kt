package com.pedrovasconcelos.beautymanager.application.queries.centers

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.application.queries.shared.PaginatedResponse
import com.pedrovasconcelos.beautymanager.domain.shared.*
import com.pedrovasconcelos.beautymanager.infra.data.entities.EmployeeEmbeddedDocument

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class GetCenterEmployeesHandler(private val mongoTemplate: MongoTemplate){
    fun handle(query: GetCenterEmployeesQuery): Either<BaseError, PaginatedResponse<GetCenterEmployeesResponse>> {
        val mongoQuery = Query(Criteria.where("_id").`is`(query.centerId))

        mongoQuery.fields()
            .include("employees")
            .include("name")
            .include("active")
            .include("email")
            .slice("employees", (query.page-1) * query.size, query.size)


        return mongoTemplate.findOne(mongoQuery, CenterEmployeesProjection::class.java, "centers")
            .toEither(NotFoundError("Center does not exist"))
            .flatMap { center ->
                val employees = center.employees
                if (employees.isNotEmpty()) {
                    val totalEmployees = getTotalEmployees(query.centerId)
                    PaginatedResponse(
                        data = employees.map { it.toGetCenterEmployeesResponse() },
                        page = query.page ,
                        size = query.size,
                        total = totalEmployees
                    ).right()
                } else {
                    NotFoundError("No employees found for the center").left()
                }
            }
    }

    private fun getTotalEmployees(centerId: String): Int{
        val countQuery = Query(Criteria.where("_id").`is`(centerId))

        countQuery.fields().include("employees")
        val center = mongoTemplate.findOne(countQuery, CenterEmployeesProjection::class.java, "centers")
        return center?.employees?.size ?: 0
    }

    private fun EmployeeEmbeddedDocument.toGetCenterEmployeesResponse() = GetCenterEmployeesResponse(
        id = id,
        name = name,
        role = role,
        active = active
    )
}

data class GetCenterEmployeesResponse(
    val id : String,
    val name: String,
    val role: String,
    val active: Boolean,

)


data class GetCenterEmployeesQuery(
    val centerId: String,
    val page : Int,
    val size : Int
)

private data class CenterEmployeesProjection(
    val employees: List<EmployeeEmbeddedDocument>
)
