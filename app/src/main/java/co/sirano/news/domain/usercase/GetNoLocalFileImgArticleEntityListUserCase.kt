package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface GetNoLocalFileImgArticleEntityListUserCase {
    suspend fun invoke(): Flow<List<ArticleEntity>>
}
