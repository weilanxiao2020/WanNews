package com.miyako.wannews.ui.main.contentPages

import android.os.Bundle
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import com.miyako.wannews.base.BaseFragment
import com.miyako.wannews.base.BaseViewModel

class NewsFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_news
    }

    override fun getViewModel(): Class<out BaseViewModel> {
        TODO("Not yet implemented")
    }

    override fun initView(): UInt {
        TODO("Not yet implemented")
    }

    override fun initData(savedInstanceState: Bundle?): UInt {
        TODO("Not yet implemented")
        LogUtils.d(TAG, "init data")
    }
}