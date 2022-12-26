package com.depromeet.threedays.policy

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.gone
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.domain.key.WEB_VIEW_URL
import com.depromeet.threedays.policy.databinding.ActivityPolicyBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PolicyActivity : BaseActivity<ActivityPolicyBinding>(R.layout.activity_policy) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEvent()
        webViewSetting()
        webViewInit()
    }

    private fun initEvent() {
        onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    clearCookie()
                    finish()
                }
            }
        )
    }

    private fun clearCookie() {
        binding.webView.clearCache(true)
    }

    private fun webViewInit() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.visible()
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
                if (newProgress == 100) {
                    binding.progressBar.gone()
                }
            }
        }
        val webViewUrl = intent.getStringExtra(WEB_VIEW_URL)!!
        binding.webView.loadUrl(webViewUrl)
    }

    private fun webViewSetting() {
        with(binding.webView.settings) {
            builtInZoomControls = true
            domStorageEnabled = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            loadsImagesAutomatically = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSupportZoom(false)
        }
    }

    override fun onDestroy() {
        binding.webView.destroy()
        super.onDestroy()
    }

}
