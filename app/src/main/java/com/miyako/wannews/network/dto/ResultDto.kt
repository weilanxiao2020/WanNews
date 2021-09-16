package com.miyako.wannews.network.dto

import com.google.gson.annotations.SerializedName

data class ResultDto<T>(
    @SerializedName("data")
    val resultData: T,
    val errorCode: Int,
    val errorMsg: String
)

data class ErrorData(val err: String)

const val ERR_EMPTY = 0xEF