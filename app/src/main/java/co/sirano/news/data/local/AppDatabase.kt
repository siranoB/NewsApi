package co.sirano.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.sirano.news.data.local.dao.ArticleDao
import co.sirano.news.data.local.dao.ImgLocalFileDao
import co.sirano.news.data.local.dao.RemoteKeysDao
import co.sirano.news.data.local.dao.ShowArticleDao
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.ImgLocalFileEntity
import co.sirano.news.data.local.entity.RemoteKeys
import co.sirano.news.data.local.entity.ShowArticleEntity

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class,
        ShowArticleEntity::class,
        ImgLocalFileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun showArticleDao(): ShowArticleDao
    abstract fun imgLocalFileDao(): ImgLocalFileDao

    companion object {
        const val DATABASE_NAME = "news.db"
    }
}
