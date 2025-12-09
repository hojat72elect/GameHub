package ca.six.hojat.gamehub.shared.data.local.authentication.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.authentication.entities.LocalAuthenticationCredentials

@Dao
interface AuthenticationTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(credentials: LocalAuthenticationCredentials)

    @Query("SELECT * FROM oauth_credentials LIMIT 1")
    suspend fun get(): LocalAuthenticationCredentials?
}
