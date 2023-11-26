package co.sirano.news.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article_table", primaryKeys = ["url", "published_at"])
data class ArticleEntity(
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String = "",
    @ColumnInfo(name = "published_at")
    val publishedAt: String = "",
    // 정렬을 위해서 timestamp 값으로 저장함.
    @ColumnInfo(name = "published_at_timestamp")
    val publishedAtTimestamp: Long = 0L,
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "isShow")
    val isShow: Boolean = false,
    @ColumnInfo(name = "img_local_file_path")
    val imgLocalFilePath: String = ""
) : Parcelable
