package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.domain.repository.Repository
import javax.inject.Inject

class UpdateArticleEntityUserCaseImpl @Inject constructor(private val repository: Repository) : UpdateArticleEntityUserCase {
    override suspend fun invoke(articleEntity: ArticleEntity) {
        repository.updateArticleEntity(articleEntity)
    }
}
