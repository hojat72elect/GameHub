package ca.six.hojat.gamehub.shared.data.local.games.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = LocalLikedGame.Schema.TABLE_NAME,
    indices = [
        Index(LocalLikedGame.Schema.GAME_ID),
        Index(LocalLikedGame.Schema.LIKE_TIMESTAMP),
    ],
)
data class LocalLikedGame(
    @ColumnInfo(name = Schema.ID) @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Schema.GAME_ID) val gameId: Int,
    @ColumnInfo(name = Schema.LIKE_TIMESTAMP) val likeTimestamp: Long,
) {

    object Schema {
        const val TABLE_NAME = "liked_games"
        const val ID = "id"
        const val GAME_ID = "game_id"
        const val LIKE_TIMESTAMP = "like_timestamp"
    }
}
