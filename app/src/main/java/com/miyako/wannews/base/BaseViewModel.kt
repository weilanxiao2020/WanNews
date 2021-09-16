package com.miyako.wannews.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miyako.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

open class BaseViewModel : ViewModel() {

    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            withTimeout(5000) {
                // block()
            }
        } catch (e: Exception) {
            //此处接收到BaseRepository里的request抛出的异常，直接赋值给error
            // error.value = e
        } finally {
            LogUtils.d("BaseViewModel", "接口请求---------->完成")
        }
    }
}