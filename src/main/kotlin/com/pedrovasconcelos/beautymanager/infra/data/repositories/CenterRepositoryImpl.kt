package com.pedrovasconcelos.beautymanager.infra.data.repositories

import CenterDocument
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDomain
import com.pedrovasconcelos.beautymanager.infra.data.mappers.toDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CenterRepositoryImpl(
    private val centerMongoRepository: CenterMongoRepository
) : CenterRepository {

    override fun saveCenter(center: Center): Either<RepositoryError, Center> =
        runCatching {
            centerMongoRepository.save(center.toDocument())
        }.mapCatching {
            it.toDomain()
        }.fold(
            onSuccess = { it.right() },
            onFailure = { RepositoryError().left() }
        )

    override fun getCenter(id: UUID): Either<RepositoryError, Center> =
        centerMongoRepository.findById(id).orElse(null)?.toDomain()?.right() ?: RepositoryError().left()
}


interface CenterMongoRepository : MongoRepository<CenterDocument, UUID>
