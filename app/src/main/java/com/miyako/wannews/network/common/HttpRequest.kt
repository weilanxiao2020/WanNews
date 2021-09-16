package com.miyako.wannews.network.common

import com.miyako.util.LogUtils
import com.miyako.wannews.base.Constants
import com.miyako.wannews.network.api.IArticleService
import com.miyako.wannews.network.api.IIndexService
import com.miyako.wannews.network.api.IProjectService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpRequest : IDataRequest() {

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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getIndexService(): IIndexService {
        var service: IDataService? = serviceMap[IIndexService::class.java]
        if (service == null) {
            LogUtils.w(TAG, "index service is null")
            service = retrofit.create(IIndexService::class.java)
            serviceMap[IIndexService::class.java] = service
            LogUtils.w(TAG, "create index service success")
        }
        LogUtils.d(TAG, "get article index success")
        return service as IIndexService
    }

    fun getArticleService() : IArticleService {
        var service: IDataService? = serviceMap[IArticleService::class.java]
        if (service == null) {
            LogUtils.w(TAG, "article service is null")
            service = retrofit.create(IArticleService::class.java)
            serviceMap[IArticleService::class.java] = service
            LogUtils.w(TAG, "create article service success")
        }
        LogUtils.d(TAG, "get article service success")
        return service as IArticleService
    }

    fun getProjectService(): IProjectService {
        var service: IDataService? = serviceMap[IProjectService::class.java]
        if (service == null) {
            LogUtils.w(TAG, "project service is null")
            service = retrofit.create(IProjectService::class.java)
            serviceMap[IProjectService::class.java] = service
            LogUtils.w(TAG, "create project service success")
        }
        LogUtils.d(TAG, "get project service success")
        return service as IProjectService
    }

}