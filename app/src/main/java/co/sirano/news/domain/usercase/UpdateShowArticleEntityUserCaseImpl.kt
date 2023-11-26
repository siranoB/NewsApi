package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ShowArticleEntity
import co.sirano.news.domain.repository.Repository
import javax.inject.Inject

class UpdateShowArticleEntityUserCaseImpl @Inject constructor(private val repository: Repository) : UpdateShowArticleEntityUserCase {
    override suspend fun invoke(showArticleEntity: ShowArticleEntity) {
        repository.updateShowArticleEntity(showArticleEntity)
    }
}
