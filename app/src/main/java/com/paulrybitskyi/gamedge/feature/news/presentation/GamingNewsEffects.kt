@file:Suppress("MatchingDeclarationName")

package com.paulrybitskyi.gamedge.feature.news.presentation

import com.paulrybitskyi.gamedge.common.ui.base.events.Command

internal sealed class GamingNewsCommand : Command {
    data class OpenUrl(val url: String) : GamingNewsCommand()
}
