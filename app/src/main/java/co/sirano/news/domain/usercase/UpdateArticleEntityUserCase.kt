package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ArticleEntity

interface UpdateArticleEntityUserCase {
    suspend fun invoke(articleEntity: ArticleEntity)
}
