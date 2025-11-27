package ca.six.hojat.gamehub.gamespot.api.common.serialization

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Gamespot(val value: String)
