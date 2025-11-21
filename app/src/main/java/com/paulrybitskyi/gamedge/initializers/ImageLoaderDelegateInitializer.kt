
package com.paulrybitskyi.gamedge.initializers

import com.paulrybitskyi.gamedge.common.ui.images.ImageLoaderInitializer
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

@BindType(contributesTo = BindType.Collection.SET)
internal class ImageLoaderDelegateInitializer @Inject constructor(
    private val imageLoaderInitializer: ImageLoaderInitializer,
) : Initializer {

    override fun init() {
        imageLoaderInitializer.init()
    }
}
