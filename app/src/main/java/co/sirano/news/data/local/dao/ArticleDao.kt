package co.sirano.news.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import co.sirano.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article_table ORDER BY published_at_timestamp DESC") // ORDER BY repository_name ASC
    fun getAll(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(vararg articleEntity: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articleEntityList: List<ArticleEntity>)

    @Update(entity = ArticleEntity::class)
    fun updateArticleEntity(vararg articleEntity: ArticleEntity)

    @Query("Delete From article_table")
    suspend fun clearArticleEntity()

    @Query("select * from article_table order by published_at_timestamp DESC")
    fun getPagingArticleEntity(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM article_table Where img_local_file_path= '' ORDER BY published_at_timestamp DESC")
    fun getNoLocalFileImgArticleEntityList(): Flow<List<ArticleEntity>>
}
