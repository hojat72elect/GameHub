
package com.paulrybitskyi.gamedge.common.ui.images

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.memory.MemoryCache
import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ImageLoaderInitializer {
    fun init()
}

@BindType
internal class CoilInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageLoaderInitializer {

    private companion object {
        const val MEMORY_CACHE_MAX_HEAP_PERCENTAGE = 0.5
    }

    override fun init() {
        Coil.setImageLoader(
            ImageLoader.Builder(context)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(MEMORY_CACHE_MAX_HEAP_PERCENTAGE)
                        .build()
                }
                .build(),
        )
    }
}
