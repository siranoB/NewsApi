package co.sirano.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "img_local_file", primaryKeys = ["url", "published_at"])
data class ImgLocalFileEntity(
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "published_at")
    val publishedAt: String = "",
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String = "",
    @ColumnInfo(name = "img_local_file_path")
    val imgLocalFilePath: String = ""
)
