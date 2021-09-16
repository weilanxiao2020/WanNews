package com.miyako.wannews.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.miyako.wannews.network.common.HttpRequest

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