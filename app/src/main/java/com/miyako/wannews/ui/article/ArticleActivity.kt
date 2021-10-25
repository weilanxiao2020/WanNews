package com.miyako.wannews.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.miyako.wannews.R
import android.webkit.WebSettings
import com.miyako.util.LogUtils
import com.miyako.wannews.base.BaseActivity

class ArticleActivity : BaseActivity() {

    companion object {
        const val KEY_URL = "key_url"
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_article
    }

    override fun initData(savedInstanceState: Bundle?) {

        val webView = findViewById<WebView>(R.id.web_view)
        //声明WebSettings子类

        val webSettings = webView.settings
        intent.getStringExtra(KEY_URL)?.let {
            LogUtils.d(TAG, "web view load url:$it")
            webView.loadUrl(it)
        }
    }

    override fun initView() {
    }

    override fun requestData() {
    }
}