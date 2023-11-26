package co.sirano.news.domain.usercase

import androidx.paging.PagingData
import co.sirano.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface GetTopHeadLinesUseCase {
    fun invoke(): Flow<PagingData<ArticleEntity>>
}
