package com.miyako.architecture.base

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    protected val TAG = javaClass.simpleName

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