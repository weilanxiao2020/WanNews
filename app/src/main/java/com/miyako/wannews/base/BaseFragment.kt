package com.miyako.wannews.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import com.miyako.wannews.ui.main.ContainerFragment
import com.miyako.wannews.ui.main.ContainerViewModel

abstract class BaseFragment : Fragment() {

    companion object {
        fun newInstance() = ContainerFragment()
    }

    protected val TAG = javaClass.simpleName

    protected lateinit var viewModel: BaseViewModel

    abstract fun getLayoutId() : Int

    abstract fun getViewModel() : Class<out BaseViewModel>

    abstract fun initView() : UInt

    abstract fun initData(savedInstanceState: Bundle?) : UInt

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d(TAG, "onActivityCreated")
        viewModel = ViewModelProvider(this).get(getViewModel())
        initView()
        initData(savedInstanceState)
    }
}