package co.sirano.news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.sirano.news.data.local.entity.ImgLocalFileEntity

@Dao
interface ImgLocalFileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ImgLocalFileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imgLocalFileEntity: List<ImgLocalFileEntity>)

    @Query("Select * From img_local_file Where url = :url and published_at = :publishedAt")
    suspend fun getImgLocalFileEntity(url: String, publishedAt: String): ImgLocalFileEntity?

    @Query("Delete From img_local_file")
    suspend fun clearShowArticleEntity()
}
