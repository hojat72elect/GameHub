package com.paulrybitskyi.gamedge.core.formatters

import com.paulrybitskyi.gamedge.R
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.common.domain.games.entities.ReleaseDate
import com.paulrybitskyi.gamedge.common.domain.games.entities.ReleaseDateCategory
import com.paulrybitskyi.gamedge.core.providers.LocaleProvider
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface GameReleaseDateFormatter {
    fun formatReleaseDate(game: Game): String
}

internal class GameReleaseDateFormatterImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val relativeDateFormatter: RelativeDateFormatter,
    private val localeProvider: LocaleProvider,
) : GameReleaseDateFormatter {

    private companion object {
        private const val COMPLETE_DATE_FORMATTING_PATTERN = "MMM dd, yyyy"
        private const val DAYLESS_DATE_FORMATTING_PATTERN = "MMMM yyyy"
    }

    override fun formatReleaseDate(game: Game): String {
        val date = game.findFirstReleaseDate()
            ?: return stringProvider.getString(R.string.unknown)

        return when (val category = date.category) {
            ReleaseDateCategory.YYYY_MMMM_DD -> date.formatCompleteDate()
            ReleaseDateCategory.YYYY_MMMM -> date.formatDaylessDate()
            ReleaseDateCategory.YYYY -> date.formatDateWithYearOnly()

            ReleaseDateCategory.YYYYQ1,
            ReleaseDateCategory.YYYYQ2,
            ReleaseDateCategory.YYYYQ3,
            ReleaseDateCategory.YYYYQ4,
                -> date.formatDateWithYearAndQuarter()

            else -> error("Unknown category: $category.")
        }
    }

    private fun Game.findFirstReleaseDate(): ReleaseDate? {
        return releaseDates
            .filter {
                it.category != ReleaseDateCategory.UNKNOWN &&
                        it.category != ReleaseDateCategory.TBD &&
                        it.date != null &&
                        it.year != null
            }
            .minByOrNull { it.date!! }
    }

    private fun ReleaseDate.formatCompleteDate(): String {
        val releaseLocalDateTime = toLocalDateTime()
        val formattedReleaseDate = DateTimeFormatter
            .ofPattern(COMPLETE_DATE_FORMATTING_PATTERN, localeProvider.getLocale())
            .format(releaseLocalDateTime)

        return buildString {
            append(formattedReleaseDate)
            append(" (")
            append(relativeDateFormatter.formatRelativeDate(releaseLocalDateTime))
            append(")")
        }
    }

    private fun ReleaseDate.formatDaylessDate(): String {
        return DateTimeFormatter
            .ofPattern(DAYLESS_DATE_FORMATTING_PATTERN, localeProvider.getLocale())
            .format(toLocalDateTime())
    }

    private fun ReleaseDate.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(checkNotNull(date)),
            ZoneId.systemDefault(),
        )
    }

    private fun ReleaseDate.formatDateWithYearOnly(): String {
        return checkNotNull(year).toString()
    }

    private fun ReleaseDate.formatDateWithYearAndQuarter(): String {
        return stringProvider.getString(
            R.string.year_with_quarter_template,
            checkNotNull(year),
            category.getQuarterString(),
        )
    }

    private fun ReleaseDateCategory.getQuarterString(): String {
        return stringProvider.getString(
            when (this) {
                ReleaseDateCategory.YYYYQ1 -> R.string.year_quarter_first
                ReleaseDateCategory.YYYYQ2 -> R.string.year_quarter_second
                ReleaseDateCategory.YYYYQ3 -> R.string.year_quarter_third
                ReleaseDateCategory.YYYYQ4 -> R.string.year_quarter_fourth

                else -> error("Unknown category $this.")
            },
        )
    }
}
