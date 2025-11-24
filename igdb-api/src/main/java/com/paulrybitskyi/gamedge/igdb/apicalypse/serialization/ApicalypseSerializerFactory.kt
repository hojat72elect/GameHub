package com.paulrybitskyi.gamedge.igdb.apicalypse.serialization

object ApicalypseSerializerFactory {

    fun create(): ApicalypseSerializer {
        return ApicalypseSerializerImpl()
    }
}
