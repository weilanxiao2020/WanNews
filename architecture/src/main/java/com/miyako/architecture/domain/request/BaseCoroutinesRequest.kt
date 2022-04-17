package com.miyako.architecture.domain.request

import com.miyako.architecture.domain.result.ResultData

/**
 * @Description 协程请求方式
 * @Author Miyako
 * @Date 2022-04-16-0016
 */
abstract class BaseCoroutinesRequest: BaseRequest() {

    abstract suspend fun <T : Any> request(call: suspend () -> ResultData<T>): ResultData<T>
}