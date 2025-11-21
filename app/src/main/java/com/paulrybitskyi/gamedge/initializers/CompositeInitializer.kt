package com.paulrybitskyi.gamedge.initializers

import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

@BindType
internal class CompositeInitializer @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>,
) : Initializer {

    override fun init() {
        initializers.forEach(Initializer::init)
    }
}
