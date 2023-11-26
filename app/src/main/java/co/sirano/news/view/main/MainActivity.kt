package co.sirano.news.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.sirano.news.common.base.BaseActivity
import co.sirano.news.common.util.MiddleDividerItemDecoration
import co.sirano.news.common.util.getScreenWidth
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.databinding.ActivityMainBinding
import co.sirano.news.view.web.WebViewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val permission: String = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    override val viewModel: MainViewModel by viewModels()

    lateinit var listAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        val isGrid = getScreenWidth() > 600 // 가로 600dp 이상 체크.
        with(binding) {
            rvNews.layoutManager = if (isGrid) GridLayoutManager(this@MainActivity, 3) else LinearLayoutManager(this@MainActivity)
            listAdapter = NewsListAdapter(isGrid) { articleEntity ->
                // 리스트 아이템이 클릭된 경우 WebViewActivity 로 화면 이동함.
                val bundle = WebViewActivity.createBundleData(articleEntity)
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.BUNDLE, bundle)
                startActivity(intent)
            }
            rvNews.adapter = listAdapter
            rvNews.itemAnimator = null // 화면 깜빡거림 제거를 위해서 적용.
            if (isGrid) {
                rvNews.addItemDecoration(MiddleDividerItemDecoration(this@MainActivity, MiddleDividerItemDecoration.ALL))
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listDataFlow.collectLatest {
                    listAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listAdapter.loadStateFlow.collectLatest { loadStates ->
                    // 에러 발생시 스낵바를 띄어서 메세지를 표시함.
                    if (loadStates.refresh is LoadState.Error) {
                        val error = (loadStates.refresh as LoadState.Error)
                        val snackBar = Snackbar.make(binding.root, error.error.message.toString(), Snackbar.LENGTH_SHORT)
                            .setAction("재시도") {
                                listAdapter.retry()
                            }
                        snackBar.show()
                    }
                    // 로딩 상태를 전달해 줌
                    handleLoading(loadStates.refresh is LoadState.Loading)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 로컬 이미지 데이터가 없는 데이터를 가져와서 Glide downloadOnly 를 사용해 file 을 생성함.
                // 해당 file 을 사용해서 별도의 file 을 생성하고 이 file path 를 DB 에 업데이트 해 줌.
                viewModel.noLocalFileImgListDataFlow.collectLatest { list ->
                    list.forEach {
                        if (it.urlToImage.isNullOrEmpty().not()) {
                            val localFile =
                                withContext(Dispatchers.IO) {
                                    Glide.with(this@MainActivity)
                                        .downloadOnly()
                                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                                        .load(it.urlToImage)
                                        .submit()
                                        .get()
                                }
                            saveFile(file = localFile, articleEntity = it)
                        }
                    }
                }
            }
        }
    }

    // 기존에 존재하는 파일을 새로운 파일로 copy 시켜줌.
    private fun saveFile(file: File?, articleEntity: ArticleEntity) {
        val path = filesDir.absolutePath + "/img/"
        val dir = File(path)
        if (!dir.exists()) {
            // 디렉토리가 있는지 없는지 부터 체크
            dir.mkdirs() // 없으면 생성
        }
        val filepath = path + System.currentTimeMillis().toString().plus(".jpg")
        val localFile = File(filepath) // 만들고자 하는 파일패스 + 네임 객체 할당
        file?.renameTo(localFile)
        viewModel.updateImgLocalFile(articleEntity = articleEntity, path = filepath)
    }

    override fun handleLoading(loading: Boolean) {
        if (isSafeBinding) {
            binding.progress.isVisible = loading
        }
    }
}
