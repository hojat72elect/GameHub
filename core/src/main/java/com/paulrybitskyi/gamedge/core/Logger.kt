package com.paulrybitskyi.gamedge.core

import android.util.Log
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

interface Logger {
    fun info(tag: String, message: String, throwable: Throwable? = null)
    fun debug(tag: String, message: String, throwable: Throwable? = null)
    fun warning(tag: String, message: String, throwable: Throwable? = null)
    fun error(tag: String, message: String, throwable: Throwable? = null)
}

@BindType
internal class GamedgeLogger @Inject constructor() : Logger {

    override fun info(tag: String, message: String, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun warning(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}
