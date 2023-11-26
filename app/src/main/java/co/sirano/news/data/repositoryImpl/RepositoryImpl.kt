package co.sirano.news.data.repositoryImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.sirano.news.data.local.AppDatabase
import co.sirano.news.data.local.dao.ArticleDao
import co.sirano.news.data.local.dao.ImgLocalFileDao
import co.sirano.news.data.local.dao.ShowArticleDao
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.ImgLocalFileEntity
import co.sirano.news.data.local.entity.ShowArticleEntity
import co.sirano.news.data.remote.ApiService
import co.sirano.news.data.source.NewsRemoteMediator
import co.sirano.news.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val articleDao: ArticleDao,
    private val showArticleDao: ShowArticleDao,
    private val imgLocalFileDao: ImgLocalFileDao
) : Repository {
    @OptIn(ExperimentalPagingApi::class)
    // 페이징 라이브러리에 사용하기 위해서 Pager 를 설정함.
    override fun getTopHeadLines(): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            pagingSourceFactory = { articleDao.getPagingArticleEntity() },
            remoteMediator = NewsRemoteMediator(
                apiService = apiService,
                appDatabase = appDatabase,
                showArticleDao = showArticleDao,
                imgLocalFileDao = imgLocalFileDao
            )
        ).flow
    }

    override suspend fun updateArticleEntity(
        articleEntity: ArticleEntity
    ) {
        articleDao.updateArticleEntity(articleEntity)
    }

    override suspend fun updateShowArticleEntity(
        showArticleEntity: ShowArticleEntity
    ) {
        showArticleDao.insert(showArticleEntity)
    }

    override suspend fun getNoLocalFileImgArticleEntityList(): Flow<List<ArticleEntity>> {
        return articleDao.getNoLocalFileImgArticleEntityList()
    }

    override suspend fun insertNoLocalFileImgArticleEntity(
        imgLocalFileEntity: ImgLocalFileEntity
    ) {
        imgLocalFileDao.insert(imgLocalFileEntity)
    }
}
