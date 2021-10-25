package com.miyako.wannews.data.repository

import com.miyako.util.LogUtils
import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.IndexTopDto
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.ResultDto

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class HomeRepository : IBaseRepository {
    private val service = HttpRequest.getInstance().getArticleService()
    private val indexService = HttpRequest.getInstance().getIndexService()

    suspend fun getHomeTopArticle(): ResultDto<List<IndexTopDto>> = request {
        LogUtils.d(TAG, "getIndexTopArticle")
        indexService.getHomeTopArticle()
    }

    suspend fun getArticleList(page: Int, pageSize: Int): ResultDto<ArticlePageDto> = request {
        LogUtils.d(TAG, "getArticleList")
        service.getArticle(page = page, pageSize = pageSize)
    }
}
