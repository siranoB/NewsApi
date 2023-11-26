package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ShowArticleEntity

interface UpdateShowArticleEntityUserCase {
    suspend fun invoke(showArticleEntity: ShowArticleEntity)
}
