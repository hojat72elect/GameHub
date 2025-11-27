@file:Suppress("MatchingDeclarationName")

package com.paulrybitskyi.gamedge.feature.settings.presentation

import com.paulrybitskyi.gamedge.common.ui.base.events.Command

internal sealed class SettingsCommand : Command {
    data class OpenUrl(val url: String) : SettingsCommand()
}
