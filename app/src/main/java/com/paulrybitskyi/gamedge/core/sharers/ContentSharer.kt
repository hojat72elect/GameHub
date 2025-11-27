package com.paulrybitskyi.gamedge.core.sharers

import android.content.Context

interface ContentSharer<Content : Any> {
    fun share(context: Context, content: Content)
}
