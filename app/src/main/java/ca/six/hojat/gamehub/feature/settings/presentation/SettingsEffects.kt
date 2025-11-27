@file:Suppress("MatchingDeclarationName")

package ca.six.hojat.gamehub.feature.settings.presentation

import ca.six.hojat.gamehub.common.ui.base.events.Command

internal sealed class SettingsCommand : Command {
    data class OpenUrl(val url: String) : SettingsCommand()
}
