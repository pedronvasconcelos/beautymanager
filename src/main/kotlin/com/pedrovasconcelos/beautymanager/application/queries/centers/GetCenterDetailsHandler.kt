package com.pedrovasconcelos.beautymanager.application.queries.centers

import CenterDocument
import arrow.core.left
import arrow.core.right
import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.NoContentResponse
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class GetCenterDetailsHandler(private val mongoTemplate: MongoTemplate) {

    fun handle(centerId: String): Either<BaseError, GetCenterDetailsResponse> {
        val query = Query(Criteria.where("_id").`is`(centerId))
        query.fields().exclude("employees")

        return mongoTemplate.findOne(query, CenterDocument::class.java)?.let { center ->
            GetCenterDetailsResponse(center.id, center.name, center.email, center.active)
                .right()
        } ?: NoContentResponse("Center with id $centerId not found")
            .left()
    }
}




data class GetCenterDetailsResponse(
    val id : String,
    val name: String,
    val email: String,
    val active: Boolean,
)

