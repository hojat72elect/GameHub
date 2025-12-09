package ca.six.hojat.gamehub.shared.data.local.authentication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oauth_credentials")
data class LocalAuthenticationCredentials(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "access_token")
    val accessToken: String,
    @ColumnInfo(name = "token_type")
    val tokenType: String,
    @ColumnInfo(name = "token_ttl")
    val tokenTtl: Long,
    @ColumnInfo(name = "expiration_time")
    val expirationTime: Long,
)
