package ca.six.hojat.gamehub.core.urlopener

import android.content.Context
import javax.inject.Inject

internal class CompositeUrlOpener @Inject constructor(
    private val urlOpeners: List<@JvmSuppressWildcards UrlOpener>,
) : UrlOpener {

    override fun openUrl(url: String, context: Context): Boolean {
        return urlOpeners.any {
            it.openUrl(url, context)
        }
    }
}
