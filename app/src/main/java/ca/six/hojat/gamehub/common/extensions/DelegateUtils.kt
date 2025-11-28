package ca.six.hojat.gamehub.common.extensions

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

fun <T> observeChanges(
    initialValue: T,
    onChange: (oldValue: T, newValue: T) -> Unit
): ReadWriteProperty<Any, T> {
    return Delegates.observable(initialValue) { _, oldValue, newValue ->
        onChange(oldValue, newValue)
    }
}

fun <T> vetoable(
    initialValue: T,
    onChange: (oldValue: T, newValue: T) -> Boolean
): ReadWriteProperty<Any, T> {
    return Delegates.vetoable(initialValue) { _, oldValue, newValue ->
        onChange(oldValue, newValue)
    }
}
