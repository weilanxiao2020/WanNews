package com.miyako.wannews.network.common

import com.miyako.util.LogUtils
import com.miyako.wannews.base.Constants
import com.miyako.wannews.network.api.IArticleService
import com.miyako.wannews.network.api.IGuideService
import com.miyako.wannews.network.api.IHomeService
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