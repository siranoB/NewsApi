package co.sirano.news.domain.usercase

import androidx.paging.PagingData
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadLinesUseCaseImpl @Inject constructor(private val repository: Repository) : GetTopHeadLinesUseCase {
    override fun invoke(): Flow<PagingData<ArticleEntity>> {
        return repository.getTopHeadLines()
    }
}
