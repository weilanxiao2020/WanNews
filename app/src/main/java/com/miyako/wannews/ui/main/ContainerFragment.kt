package com.miyako.wannews.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miyako.wannews.R
import com.miyako.wannews.base.BaseFragment
import com.miyako.wannews.base.BaseViewModel

class ContainerFragment : BaseFragment() {

    companion object {
        fun newInstance() = ContainerFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.container_fragment
    }

    override fun getViewModel(): Class<out BaseViewModel> {
        return ContainerViewModel::class.java
    }

    override fun initView(): UInt {
        TODO("Not yet implemented")
    }

    override fun initData(savedInstanceState: Bundle?): UInt {
        TODO("Not yet implemented")
    }

}