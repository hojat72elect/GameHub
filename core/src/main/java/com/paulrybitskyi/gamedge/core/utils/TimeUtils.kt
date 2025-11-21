package com.paulrybitskyi.gamedge.core.utils

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return atZone(zoneId).toInstant().toEpochMilli()
}
