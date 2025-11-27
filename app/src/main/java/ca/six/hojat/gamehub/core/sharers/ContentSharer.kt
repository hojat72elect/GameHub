package ca.six.hojat.gamehub.core.sharers

import android.content.Context

interface ContentSharer<Content : Any> {
    fun share(context: Context, content: Content)
}
