package co.sirano.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "show_article", primaryKeys = ["url", "published_at"])
data class ShowArticleEntity(
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    @ColumnInfo(name = "published_at_timestamp")
    val publishedAtTimestamp: Long = 0L,
    @ColumnInfo(name = "isShow")
    val isShow: Boolean = false
)
