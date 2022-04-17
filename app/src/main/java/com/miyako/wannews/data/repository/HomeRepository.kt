package com.miyako.wannews.data.repository

import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.domain.ResultSource
import com.miyako.architecture.domain.request.BaseCoroutinesRequest
import com.miyako.architecture.domain.request.BaseRequest
import com.miyako.architecture.domain.request.HttpCoroutinesRequest
import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.IndexTopDto
import com.miyako.wannews.network.common.HttpRequest

/**
 * @Description 首页数据存储，用于在ViewModel中请求数据
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class HomeRepository(
    private var requestImpl: BaseRequest = HttpCoroutinesRequest()
): BaseRepository() {

    private val service = HttpRequest.getInstance().getArticleService()
    private val indexService = HttpRequest.getInstance().getIndexService()

    suspend fun getHomeTopArticle(): ResultData<List<IndexTopDto>> {
       return if (requestExecute<BaseCoroutinesRequest>(requestImpl) != null) {
            LogUtils.d(TAG, "getIndexTopArticle")
            indexService.getHomeTopArticle()
        } else {
            ResultData.emptyList()
        }
    }

    suspend fun getArticleList(page: Int, pageSize: Int): ResultData<ArticlePageDto> {
        return if (requestExecute<BaseCoroutinesRequest>(requestImpl) != null) {
            (requestImpl as BaseCoroutinesRequest).request {
                LogUtils.d(TAG, "getArticleList")
                service.getArticle(page = page, pageSize = pageSize)
            }
        } else {
            ResultData.empty()
        }
    }
}
