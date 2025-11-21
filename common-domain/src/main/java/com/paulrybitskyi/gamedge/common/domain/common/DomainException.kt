
package com.paulrybitskyi.gamedge.common.domain.common

import com.paulrybitskyi.gamedge.common.domain.common.entities.Error

class DomainException(
    val error: Error,
    cause: Throwable? = null,
) : Exception(error.toString(), cause)
