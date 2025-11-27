package ca.six.hojat.gamehub.common.domain.common

import ca.six.hojat.gamehub.common.domain.common.entities.Error

class DomainException(
    val error: Error,
    cause: Throwable? = null,
) : Exception(error.toString(), cause)
