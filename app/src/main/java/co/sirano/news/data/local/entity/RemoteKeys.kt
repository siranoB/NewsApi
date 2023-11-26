package co.sirano.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "remote_key", primaryKeys = ["url", "published_at"])
data class RemoteKeys(
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    @ColumnInfo(name = "published_at_timestamp")
    val publishedAtTimestamp: Long = 0L,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "current_page")
    val currentPage: Int,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
