package com.miyako.wannews.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.miyako.wannews.R
import com.miyako.wannews.data.HttpRequest

abstract class BaseActivity : ComponentActivity() {

    val TAG = javaClass.simpleName

    val httpRequest = HttpRequest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData(savedInstanceState)
        initView()
    }

    abstract fun getLayoutId() : Int

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initView()

    abstract fun requestData()
}