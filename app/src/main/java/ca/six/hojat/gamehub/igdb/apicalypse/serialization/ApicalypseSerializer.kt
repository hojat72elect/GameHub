package ca.six.hojat.gamehub.igdb.apicalypse.serialization

interface ApicalypseSerializer {
    fun serialize(clazz: Class<*>): String
}
