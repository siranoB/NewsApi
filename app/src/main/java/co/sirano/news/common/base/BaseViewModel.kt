package co.sirano.news.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// loading 과 같은 공통 데이터 처리를 위한 BaseViewModel
abstract class BaseViewModel() : ViewModel() {
    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean>
        get() = _loadingFlow

    protected suspend fun setLoadingFlow(loading: Boolean) {
        _loadingFlow.emit(loading)
    }
}
