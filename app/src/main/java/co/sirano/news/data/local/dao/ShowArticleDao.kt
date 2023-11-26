package co.sirano.news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.sirano.news.data.local.entity.ShowArticleEntity

@Dao
interface ShowArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ShowArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(showArticleList: List<ShowArticleEntity>)

    @Query("Select * From show_article Where url = :url and published_at = :publishedAt")
    suspend fun getShowArticleEntity(url: String, publishedAt: String): ShowArticleEntity?

    @Query("Delete From show_article")
    suspend fun clearShowArticleEntity()
}
