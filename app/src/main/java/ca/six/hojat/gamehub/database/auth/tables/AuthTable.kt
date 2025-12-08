package ca.six.hojat.gamehub.database.auth.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.database.auth.entities.DbOauthCredentials

@Dao
interface AuthTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(credentials: DbOauthCredentials)

    @Query("SELECT * FROM oauth_credentials LIMIT 1")
    suspend fun get(): DbOauthCredentials?
}
