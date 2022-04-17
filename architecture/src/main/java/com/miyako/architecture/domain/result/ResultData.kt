package com.miyako.architecture.domain.result

import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.util.LogUtils

/**
 * @Description 请求结果数据
 * @param responseStatus 响应状态
 * @param responseData 响应数据
 * @Author Miyako
 * @Date 2022-04-16-0016
 */
data class ResultData<T> (
    val responseStatus: ResponseStatus,
    val responseData: T?
) {
    companion object {
        const val TAG = "ResultData"

        inline fun <reified T> empty(): ResultData<T> {
            LogUtils.w(TAG, "Load empty ResultData")
            return ResultData(ResponseStatus.netEmpty(), null)
        }

        inline fun <reified T> emptyList(): ResultData<List<T>> {
            LogUtils.w(TAG, "Load empty list ResultData")
            return ResultData(ResponseStatus.netEmpty(), listOf())
        }
    }
}