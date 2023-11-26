package co.sirano.news.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// ViewBinding 을 공용으로 사용하기 위한 BaseActivity
abstract class BaseActivity<B : ViewBinding>(
    private val inflate: (LayoutInflater) -> B
) : AppCompatActivity() {
    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    val isSafeBinding: Boolean
        get() {
            return _binding != null
        }

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingFlow.collectLatest {
                    handleLoading(it)
                }
            }
        }
    }

    open fun handleLoading(loading: Boolean) {
    }
}
