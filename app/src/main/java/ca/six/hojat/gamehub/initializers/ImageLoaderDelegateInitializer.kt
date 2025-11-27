package ca.six.hojat.gamehub.initializers

import ca.six.hojat.gamehub.common.ui.images.ImageLoaderInitializer
import javax.inject.Inject

internal class ImageLoaderDelegateInitializer @Inject constructor(
    private val imageLoaderInitializer: ImageLoaderInitializer,
) : Initializer {

    override fun init() {
        imageLoaderInitializer.init()
    }
}
