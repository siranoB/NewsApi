package co.sirano.news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.sirano.news.data.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where url = :url and published_at = :publishedAt")
    suspend fun getRemoteKey(url: String, publishedAt: String): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long
}
