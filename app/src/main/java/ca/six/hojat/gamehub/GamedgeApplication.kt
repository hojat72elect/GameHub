package ca.six.hojat.gamehub

import android.app.Application
import ca.six.hojat.gamehub.initializers.Initializer
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
