package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoLocalFileImgArticleEntityListUserCaseImpl @Inject constructor(private val repository: Repository) : GetNoLocalFileImgArticleEntityListUserCase {
    override suspend fun invoke(): Flow<List<ArticleEntity>> {
        return repository.getNoLocalFileImgArticleEntityList()
    }
}
