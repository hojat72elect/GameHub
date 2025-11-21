package com.paulrybitskyi.gamedge.core.urlopener

import android.content.Context

interface UrlOpener {

    /**
     * Tries to open a url.
     *
     * @param url the url to open
     * @param context the activity context
     *
     * @return true if the url was opened; false otherwise
     */
    fun openUrl(url: String, context: Context): Boolean
}
