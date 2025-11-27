package ca.six.hojat.gamehub.common.ui

import androidx.compose.runtime.staticCompositionLocalOf
import ca.six.hojat.gamehub.core.providers.NetworkStateProvider
import ca.six.hojat.gamehub.core.sharers.TextSharer
import ca.six.hojat.gamehub.core.urlopener.UrlOpener

val LocalUrlOpener = staticCompositionLocalOf<UrlOpener> {
    error("UrlOpener not provided.")
}

val LocalTextSharer = staticCompositionLocalOf<TextSharer> {
    error("TextSharer not provided.")
}

val LocalNetworkStateProvider = staticCompositionLocalOf<NetworkStateProvider> {
    error("NetworkStateProvider not provided.")
}
