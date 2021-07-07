package com.miyako.wannews.data

import com.miyako.util.LogUtils
import com.miyako.wannews.base.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpRequest : IDataRequest() {

    companion object Obj {
        private val obj = HttpRequest()

        fun getInstance(): HttpRequest {
            return obj;
        }
    }

    val retrofit: Retrofit

    val serviceMap: HashMap<Class<*>, IDataService> = HashMap()

    init {
        LogUtils.d(TAG, "初始化Retrofit")
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getArticleService() : ArticleService {
        var service: IDataService? = serviceMap[ArticleService::class.java]
        if (service == null) {
            LogUtils.w(TAG, "article service is null")
            service = retrofit.create(ArticleService::class.java)
            serviceMap.put(ArticleService::class.java, service)
            LogUtils.w(TAG, "create article service success")

        }
        LogUtils.d(TAG, "get article service success")
        return service as ArticleService
    }

}