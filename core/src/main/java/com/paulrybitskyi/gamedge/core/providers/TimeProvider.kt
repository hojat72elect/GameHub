package com.paulrybitskyi.gamedge.core.providers

import java.time.LocalDateTime
import javax.inject.Inject

interface TimeProvider {
    fun getCurrentDateTime(): LocalDateTime
}

internal class TimeProviderImpl @Inject constructor() : TimeProvider {

    override fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }
}
