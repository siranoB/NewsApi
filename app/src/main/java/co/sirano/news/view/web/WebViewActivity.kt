package co.sirano.news.view.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.sirano.news.BuildConfig
import co.sirano.news.common.base.BaseActivity
import co.sirano.news.common.util.parcelable
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.databinding.ActivityWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewActivity : BaseActivity<ActivityWebviewBinding>(ActivityWebviewBinding::inflate) {

    override val viewModel: WebViewViewModel by viewModels()

    // Back 키 처리.
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        val data = intent.getBundleExtra(BUNDLE)?.parcelable<ArticleEntity>(BUNDLE_DATA)
        with(binding) {
            setupWebView()
            // 해당 article 진입 여부를 체크함.
            viewModel.updateShowDb(data)
            binding.webView.loadUrl(data?.url ?: "")
        }
    }

    // 기본 WebView 설정.
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.let { webView ->
            webView.settings.javaScriptEnabled = true

            // Enable and setup web view cache
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

            // Enable zooming in web view
            webView.settings.setSupportZoom(true)
            webView.setInitialScale(1)
            webView.settings.builtInZoomControls = true
            webView.settings.displayZoomControls = false

            // Enable disable images in web view
            webView.settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            webView.settings.loadsImagesAutomatically = true
            webView.settings.allowFileAccess = true
            // More web view settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webView.settings.safeBrowsingEnabled = true // api 26
            }
            webView.settings.textZoom = 100
            // settings.pluginState = WebSettings.PluginState.ON
            webView.settings.useWideViewPort = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.mediaPlaybackRequiresUserGesture = false

            // More optional settings, you can enable it by yourself
            webView.settings.domStorageEnabled = true
            webView.settings.setSupportMultipleWindows(true) // false면 현재 webView에서! 유튜브 동영상이 플레이
            webView.settings.allowContentAccess = true
            webView.settings.setGeolocationEnabled(true)
            webView.settings.allowUniversalAccessFromFileURLs = true
            webView.settings.allowFileAccess = true

            // WebView settings
            webView.fitsSystemWindows = true

            if (BuildConfig.DEBUG) {
                WebView.setWebContentsDebuggingEnabled(true)
            }

            /*
                if SDK version is greater of 19 then activate hardware acceleration
                otherwise activate software acceleration
            */
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeAllCookies(null)
            cookieManager.setAcceptThirdPartyCookies(webView, true)

            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    if (isSafeBinding) {
                        binding.progress.isVisible = true
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (isSafeBinding) {
                        binding.progress.isVisible = false
                    }
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    super.onReceivedError(view, request, error)
                }

                override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                    super.onReceivedHttpError(view, request, errorResponse)
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    super.onReceivedSslError(view, handler, error)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }

                override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
                    return false
                }
            }

            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (isSafeBinding) {
                        binding.progress.progress = newProgress
                    }
                }
            }
        }
    }

    companion object {
        const val BUNDLE = "BUNDLE"
        private const val BUNDLE_DATA = "BUNDLE_DATA"

        fun createBundleData(data: ArticleEntity): Bundle {
            return Bundle().apply {
                putParcelable(BUNDLE_DATA, data)
            }
        }
    }
}
