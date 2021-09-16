package com.miyako.wannews.data.repository

import com.miyako.util.LogUtils
import com.miyako.wannews.network.common.HttpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class NewsRepository : IBaseRepository {
    private val service = HttpRequest.getInstance().getArticleService()

    suspend fun getArticleList(id: Int) = withContext(Dispatchers.IO) {
        try {
            LogUtils.d(TAG, "get info data idx:${id}")
            service.getArticle(id)
        }catch (e: Exception){
            LogUtils.e(TAG, "info data failed: ${e.message}")
            null
        }
    }

    suspend fun getArticleList(page: Int, pageSize: Int) = withContext(Dispatchers.IO) {
        try {
            service.getArticle(page = page, pageSize = pageSize)
        }catch (e: Exception){
            LogUtils.e(TAG, "info data failed: ${e.message}")
            null
        }
    }
}
