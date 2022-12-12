package ca.on.hojat.gamenews.shared.data.games.datastores.database

import ca.on.hojat.gamenews.shared.core.providers.TimestampProvider
import ca.on.hojat.gamenews.shared.database.games.entities.DbLikedGame
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import javax.inject.Singleton

private const val ENTITY_AUTOGENERATED_ID_INDICATOR = 0

internal interface LikedGameFactory {
    fun createLikedGame(gameId: Int): DbLikedGame
}

@Singleton
@BindType
internal class LikedGameFactoryImpl @Inject constructor(
    private val timestampProvider: TimestampProvider,
) : LikedGameFactory {

    override fun createLikedGame(gameId: Int): DbLikedGame {
        return DbLikedGame(
            id = ENTITY_AUTOGENERATED_ID_INDICATOR,
            gameId = gameId,
            likeTimestamp = timestampProvider.getUnixTimestamp()
        )
    }
}