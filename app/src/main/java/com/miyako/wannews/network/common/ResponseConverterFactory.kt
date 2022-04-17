package com.miyako.wannews.network.common

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.domain.ResultSource
import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.StringReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @Description 自定义Retrofit2响应数据转换器，因为自定义的响应数据格式和WanAndroid接口返回格式冲突，无法使用Gson自动转换，需要手动转换
 * @Author Miyako
 * @Date 2022-04-16-0016
 */
class ResponseConverterFactory: Converter.Factory() {

    companion object {
        fun create(): ResponseConverterFactory {
            return ResponseConverterFactory()
        }
    }

    private val gsonConverterFactory = GsonConverterFactory.create()

    override fun stringConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, String>? {
        return gsonConverterFactory.stringConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return ResponseConverter(type)
    }

    class ResponseConverter(val type: Type): Converter<ResponseBody, ResultData<*>> {

        companion object {
            const val TAG = "ResponseConverter"
            val GSON = Gson()
        }

        override fun convert(value: ResponseBody): ResultData<*> {
            val json = value.string()
            // val json = "{\"data\":{\"curPage\":2326,\"datas\":[],\"offset\":46500,\"over\":true,\"pageCount\":609,\"size\":20,\"total\":12166},\"errorCode\":0,\"errorMsg\":\"\"}"
            LogUtils.d(TAG, "custom parser json: $json")
            var code = 0
            var msg = ""
            var data: Any? = null
            try {
                val jsonParser = JsonParser().parse(json)
                if (jsonParser.isJsonObject) {
                    val jsonObject = jsonParser.asJsonObject
                    if (jsonObject.has("errorCode")) {
                        code = jsonObject.get("errorCode").asInt
                        LogUtils.d(TAG, "code: $code")
                    }
                    if (jsonObject.has("errorMsg")) {
                        msg = jsonObject.get("errorMsg").asString
                        LogUtils.d(TAG, "msg: $msg")
                    }
                    if (jsonObject.has("data")) {
                        val dataJson = jsonObject.get("data").toString()
                        LogUtils.d(TAG, "data: $dataJson")
                        LogUtils.d(TAG, "type: $type")
                        if (type is ParameterizedType) {
                            val type1 = type.actualTypeArguments[0]
                            LogUtils.d(TAG, "type: $type1")
                            data = GSON.getAdapter(TypeToken.get(type1)).read(JsonReader(StringReader(dataJson)))
                        }
                    }
                }
            } catch (e: Exception) {
                LogUtils.d(TAG, "parser error: $e")
            }

            val result = ResultData(ResponseStatus(code, msg, true, ResultSource.NETWORK), data)
            LogUtils.d(TAG, "convert result: $result")
            return result
        }
    }
}