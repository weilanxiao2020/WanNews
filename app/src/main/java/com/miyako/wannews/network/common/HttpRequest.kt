package com.miyako.wannews.network.common

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.domain.ResultSource
import com.miyako.architecture.domain.request.HttpCoroutinesRequest
import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import com.miyako.wannews.base.Constants
import com.miyako.wannews.network.api.IArticleService
import com.miyako.wannews.network.api.IGuideService
import com.miyako.wannews.network.api.IHomeService
import com.miyako.wannews.network.api.IProjectService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.StringReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 网络请求操作类
 */
class HttpRequest : HttpCoroutinesRequest() {

    companion object Obj {
        private val obj = HttpRequest()

        fun getInstance(): HttpRequest {
            return obj
        }
    }

    private val retrofit: Retrofit

   // 加载的Retrofit接口代理缓存
    private val serviceMap: HashMap<Class<*>, IDataService> = HashMap()

    init {
        LogUtils.d(TAG, "初始化Retrofit")
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            // .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ResponseConverterFactory.create())
            .build()
    }

    fun getIndexService(): IHomeService {
        var service: IDataService? = serviceMap[IHomeService::class.java]
        if (service == null) {
            LogUtils.w(TAG, "index service is null")
            service = retrofit.create(IHomeService::class.java)
            serviceMap[IHomeService::class.java] = service
            LogUtils.w(TAG, "create index service success")
        }
        LogUtils.d(TAG, "get article index success")
        return service as IHomeService
    }

    private fun <T> getService(clazz: Class<out IDataService>): T {
        var service: IDataService? = serviceMap[clazz]
        if (service == null) {
            LogUtils.w(TAG, "${clazz.simpleName} is null")
            service = retrofit.create(clazz)
            serviceMap[clazz] = service
            LogUtils.w(TAG, "create ${clazz.simpleName} success")
        }
        LogUtils.d(TAG, "get ${clazz.simpleName} success")
        return service as T
    }

    fun getArticleService() : IArticleService {
        return getService(IArticleService::class.java)
    }

    fun getProjectService(): IProjectService {
        return getService(IProjectService::class.java)
    }

    fun getGuideService(): IGuideService {
        return getService(IGuideService::class.java)
    }

}