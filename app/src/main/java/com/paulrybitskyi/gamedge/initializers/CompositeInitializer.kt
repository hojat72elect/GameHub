package com.paulrybitskyi.gamedge.initializers

import javax.inject.Inject

internal class CompositeInitializer @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>,
) : Initializer {

    override fun init() {
        initializers.forEach(Initializer::init)
    }
}
