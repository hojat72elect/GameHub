package com.paulrybitskyi.gamedge

import android.app.Application
import com.paulrybitskyi.gamedge.initializers.Initializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class GamedgeApplication : Application() {

    @Inject
    lateinit var initializer: Initializer

    override fun onCreate() {
        super.onCreate()

        initializer.init()
    }
}
