package ca.six.hojat.gamehub.core.providers

import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface TimestampProvider {
    fun getUnixTimestamp(timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Long
}

internal class TimestampProviderImpl @Inject constructor() : TimestampProvider {

    override fun getUnixTimestamp(timeUnit: TimeUnit): Long {
        return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }
}
