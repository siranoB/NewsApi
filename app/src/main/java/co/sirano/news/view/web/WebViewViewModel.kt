package co.sirano.news.view.web

import androidx.lifecycle.viewModelScope
import co.sirano.news.common.base.BaseViewModel
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.data.local.entity.ShowArticleEntity
import co.sirano.news.domain.usercase.UpdateArticleEntityUserCase
import co.sirano.news.domain.usercase.UpdateShowArticleEntityUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val updateArticleEntityUserCase: UpdateArticleEntityUserCase,
    private val updateShowArticleEntityUserCase: UpdateShowArticleEntityUserCase
) : BaseViewModel() {

    // 웹뷰 진입 여부 값을 DB 에 반영함.
    fun updateShowDb(data: ArticleEntity?) {
        data?.let { data ->
            viewModelScope.launch {
                updateArticleEntityUserCase.invoke(
                    data.copy(isShow = true)
                )
                updateShowArticleEntityUserCase.invoke(
                    ShowArticleEntity(
                        url = data.url,
                        publishedAt = data.publishedAt,
                        publishedAtTimestamp = data.publishedAtTimestamp,
                        isShow = true
                    )
                )
            }
        }
    }
}
