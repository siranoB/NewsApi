package co.sirano.news.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import co.sirano.news.common.util.DateUtil
import co.sirano.news.data.local.AppDatabase
import co.sirano.news.data.local.dao.ImgLocalFileDao
import co.sirano.news.data.local.dao.ShowArticleDao
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.RemoteKeys
import co.sirano.news.data.remote.ApiService
import javax.inject.Inject

// 로컬 데이터와 API 데이터를 함께 사용하기 위해서 RemoteMediator 를 사용함.
@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val showArticleDao: ShowArticleDao,
    private val imgLocalFileDao: ImgLocalFileDao
) : RemoteMediator<Int, ArticleEntity>() {
    /** LoadType.Append
     * When we need to load data at the end of the currently loaded data set, the load parameter is LoadType.APPEND
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { articleData ->
            appDatabase.remoteKeysDao().getRemoteKey(articleData.url, articleData.publishedAt)
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleEntity>): MediatorResult {
        try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    // 데이터를 처음으로 로드
                    1
                }

                LoadType.PREPEND -> {
                    // 이전 데이터 로드.
                    // 최신 데이터를 로드 하도록 함.
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    // 이전에 로드한 데이터의 끝 부분
                    val remoteKeys = getLastRemoteKey(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with endOfPaginationReached = false because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                    // the end of pagination for append.
                    val nextKey = remoteKeys?.nextKey
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val apiResponse = apiService.getTopHeadLines(page = page).body()

            val articles = apiResponse?.articles ?: emptyList()
            val endOfPaginationReached = articles.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.articleDao().clearArticleEntity()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                // 2023-11-23T13:31:50Z
                val articleEntityList = articles.map {
                    ArticleEntity(
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        publishedAtTimestamp = DateUtil.convertToMilliSecond(it.publishedAt),
                        content = it.content,
                        isShow = showArticleDao.getShowArticleEntity(url = it.url, publishedAt = it.publishedAt) != null,
                        imgLocalFilePath = imgLocalFileDao.getImgLocalFileEntity(url = it.url, publishedAt = it.publishedAt)?.imgLocalFilePath ?: ""
                    )
                }
                val remoteKeys = articleEntityList.map {
                    RemoteKeys(
                        url = it.url,
                        publishedAt = it.publishedAt,
                        publishedAtTimestamp = it.publishedAtTimestamp,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                        createdAt = System.currentTimeMillis()
                    )
                }

                appDatabase.remoteKeysDao().insertAll(remoteKeys)
                appDatabase.articleDao().insertAll(articleEntityList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
