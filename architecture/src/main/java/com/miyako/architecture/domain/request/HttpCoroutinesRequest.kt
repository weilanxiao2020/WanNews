package com.miyako.architecture.domain.request

import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description 互联网请求方式
 * @Author Miyako
 * @Date 2022-04-16-0016
 */
open class HttpCoroutinesRequest: BaseCoroutinesRequest() {

    override suspend fun <T : Any> request(call: suspend () -> ResultData<T>): ResultData<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            LogUtils.d(TAG, "接口返回数据-----> ${this.toString()}")
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理
            if (responseStatus.isSuccess) {
                when (responseStatus.responseCode) {
                    //一般0和200是请求成功，直接返回数据
                    0, 200 -> this
                    // 100, 401 -> throw TokenInvalidException(errorMsg)
                    // 403 -> throw NoPermissionsException(errorMsg)
                    // 404 -> throw NotFoundException(errorMsg)
                    // 500 -> throw InterfaceErrException(errorMsg)
                    else -> {
                        LogUtils.e(TAG, "网络请求错误：${responseStatus.responseMsg}")
                        throw Exception(responseStatus.responseMsg)
                    }
                }
            } else {
                LogUtils.e(TAG, "请求失败：$responseStatus.responseMsg")
                throw Exception(responseStatus.responseMsg)
            }
        }
    }
}