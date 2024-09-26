package com.pedrovasconcelos.beautymanager.domain.centers

import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.shared.RepositoryError
import java.util.*

interface CenterRepository {
    fun saveCenter(center: Center): Either<RepositoryError, Center>
    fun getCenter(id: UUID): Either<RepositoryError, Center>
}