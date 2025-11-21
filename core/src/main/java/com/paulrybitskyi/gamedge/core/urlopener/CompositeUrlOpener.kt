
package com.paulrybitskyi.gamedge.core.urlopener

import android.content.Context
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

@BindType
internal class CompositeUrlOpener @Inject constructor(
    private val urlOpeners: List<@JvmSuppressWildcards UrlOpener>,
) : UrlOpener {

    override fun openUrl(url: String, context: Context): Boolean {
        return urlOpeners.any {
            it.openUrl(url, context)
        }
    }
}
