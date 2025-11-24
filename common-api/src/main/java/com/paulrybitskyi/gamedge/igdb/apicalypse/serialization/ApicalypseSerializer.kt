package com.paulrybitskyi.gamedge.igdb.apicalypse.serialization

interface ApicalypseSerializer {
    fun serialize(clazz: Class<*>): String
}
