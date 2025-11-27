@file:Suppress("MatchingDeclarationName")

package ca.six.hojat.gamehub.feature.news.presentation

import ca.six.hojat.gamehub.common.ui.base.events.Command

internal sealed class GamingNewsCommand : Command {
    data class OpenUrl(val url: String) : GamingNewsCommand()
}
