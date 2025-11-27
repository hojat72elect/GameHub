package ca.six.hojat.gamehub.feature.news.data.throttling

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import ca.six.hojat.gamehub.core.providers.TimestampProvider
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class ArticlesRefreshingThrottlerImpl @Inject constructor(
    private val articlesPreferences: DataStore<Preferences>,
    private val timestampProvider: TimestampProvider,
) : ArticlesRefreshingThrottler {

    private companion object {
        private val ARTICLES_REFRESH_TIMEOUT = TimeUnit.MINUTES.toMillis(10L)
    }

    override suspend fun canRefreshArticles(key: String): Boolean {
        return articlesPreferences.data
            .map { it[longPreferencesKey(key)] ?: 0L }
            .map { timestampProvider.getUnixTimestamp() > (it + ARTICLES_REFRESH_TIMEOUT) }
            .first()
    }

    override suspend fun updateArticlesLastRefreshTime(key: String) {
        articlesPreferences.edit {
            it[longPreferencesKey(key)] = timestampProvider.getUnixTimestamp()
        }
    }
}
