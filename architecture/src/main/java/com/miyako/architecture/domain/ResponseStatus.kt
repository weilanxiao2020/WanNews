package com.miyako.architecture.domain

/**
 * @Description 响应状态
 * @param responseCode 响应状态码
 * @param responseMsg 响应消息
 * @param isSuccess 响应是否成功
 * @param source 响应来源
 * @Author Miyako
 * @Date 2022-04-16-0016
 */
data class ResponseStatus(
    val responseCode: Int,
    val responseMsg: String,
    val isSuccess: Boolean = true,
    val source: Enum<ResultSource> = ResultSource.NETWORK
) {
    companion object {
        fun netEmpty(): ResponseStatus {
            return ResponseStatus(0, "", true, ResultSource.NETWORK)
        }
    }
}

