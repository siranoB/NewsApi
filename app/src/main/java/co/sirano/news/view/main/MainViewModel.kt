package co.sirano.news.view.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.sirano.news.common.base.BaseViewModel
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.ImgLocalFileEntity
import co.sirano.news.domain.usercase.GetNoLocalFileImgArticleEntityListUserCase
import co.sirano.news.domain.usercase.GetTopHeadLinesUseCase
import co.sirano.news.domain.usercase.InsertImgLocalFileUserCase
import co.sirano.news.domain.usercase.UpdateArticleEntityUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase,
    private val getNoLocalFileImgArticleEntityListUserCase: GetNoLocalFileImgArticleEntityListUserCase,
    private val updateArticleEntityUserCase: UpdateArticleEntityUserCase,
    private val insertImgLocalFileUserCase: InsertImgLocalFileUserCase
) : BaseViewModel() {

    // 로컬 이미지 데이터가 없는 데이터
    private var _noLocalFileImgListDataFlow = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val noLocalFileImgListDataFlow = _noLocalFileImgListDataFlow.asStateFlow()

    val listDataFlow: Flow<PagingData<ArticleEntity>> = getTopHeadLinesUseCase.invoke().cachedIn(viewModelScope)

    init {
        // 로컬 이미지 데이터가 없는 데이터를 room flow 를 사용해서 이를 가져옴.
        viewModelScope.launch {
            getNoLocalFileImgArticleEntityListUserCase.invoke().collect {
                viewModelScope.launch {
                    _noLocalFileImgListDataFlow.emit(it)
                }
            }
        }
    }

    // 로컬 이미지 파일이 생성된 경우이를 DB 에 업데이트 해 줌.
    fun updateImgLocalFile(articleEntity: ArticleEntity, path: String) {
        viewModelScope.launch {
            updateArticleEntityUserCase.invoke(articleEntity.copy(imgLocalFilePath = path))
            insertImgLocalFileUserCase.invoke(
                ImgLocalFileEntity(
                    url = articleEntity.url,
                    publishedAt = articleEntity.publishedAt,
                    urlToImage = articleEntity.urlToImage,
                    imgLocalFilePath = path
                )
            )
        }
    }
}
