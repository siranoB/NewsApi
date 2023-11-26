package co.sirano.news.domain.repository

import androidx.paging.PagingData
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.ImgLocalFileEntity
import co.sirano.news.data.local.entity.ShowArticleEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getTopHeadLines(): Flow<PagingData<ArticleEntity>>

    suspend fun updateArticleEntity(
        articleEntity: ArticleEntity
    )

    suspend fun updateShowArticleEntity(
        showArticleEntity: ShowArticleEntity
    )

    suspend fun getNoLocalFileImgArticleEntityList(): Flow<List<ArticleEntity>>

    suspend fun insertNoLocalFileImgArticleEntity(
        imgLocalFileEntity: ImgLocalFileEntity
    )
}
