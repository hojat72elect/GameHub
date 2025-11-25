package com.paulrybitskyi.gamedge.initializers

import com.paulrybitskyi.gamedge.common.ui.images.ImageLoaderInitializer
import javax.inject.Inject

internal class ImageLoaderDelegateInitializer @Inject constructor(
    private val imageLoaderInitializer: ImageLoaderInitializer,
) : Initializer {

    override fun init() {
        imageLoaderInitializer.init()
    }
}
