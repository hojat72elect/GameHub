package ca.six.hojat.gamehub.core.urlopener

import android.content.Context
import android.content.Intent
import android.net.Uri
import ca.six.hojat.gamehub.common.extensions.canIntentBeHandled
import ca.six.hojat.gamehub.core.utils.attachNewTaskFlagIfNeeded
import javax.inject.Inject

@UrlOpenerKey(UrlOpenerKey.Type.BROWSER)
internal class BrowserUrlOpener @Inject constructor() : UrlOpener {

    override fun openUrl(url: String, context: Context): Boolean {
        val intent = createIntent(url, context)

        return if (context.canIntentBeHandled(intent)) {
            context.startActivity(intent)
            true
        } else {
            false
        }
    }

    private fun createIntent(url: String, context: Context): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            attachNewTaskFlagIfNeeded(context)
        }
    }
}
