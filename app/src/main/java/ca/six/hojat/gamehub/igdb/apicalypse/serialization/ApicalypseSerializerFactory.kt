package ca.six.hojat.gamehub.igdb.apicalypse.serialization

object ApicalypseSerializerFactory {

    fun create(): ApicalypseSerializer {
        return ApicalypseSerializerImpl()
    }
}
