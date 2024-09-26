package com.pedrovasconcelos.beautymanager.domain.centers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.pedrovasconcelos.beautymanager.domain.shared.BaseError
import com.pedrovasconcelos.beautymanager.domain.shared.ValidationError
import java.util.UUID

data class Center (val id : UUID,
                   val active : Boolean,
                   val name : String,
                   val email : String,
    )

fun createCenter(name: String, email: String): Either<BaseError, Center> {
    return if (name.isBlank() || email.isBlank()) {
        ValidationError("Name and email must not be blank").left()
    } else {
        Center(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            active = true
        ).right()
    }
}