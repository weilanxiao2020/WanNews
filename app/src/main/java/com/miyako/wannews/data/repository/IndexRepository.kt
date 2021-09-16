package com.miyako.wannews.data.repository

import android.util.Log
import com.miyako.util.LogUtils
import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.IndexTopDto
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.ERR_EMPTY
import com.miyako.wannews.network.dto.ErrorData
import com.miyako.wannews.network.dto.ResultDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class IndexRepository : IBaseRepository {
    private val service = HttpRequest.getInstance().getArticleService()
    private val indexService = HttpRequest.getInstance().getIndexService()

    suspend fun getIndexTopArticle(): ResultDto<List<IndexTopDto>> = request {
        LogUtils.d(TAG, "getIndexTopArticle")
        indexService.getIndexTopArticle()
    }

    suspend fun getArticleList(page: Int, pageSize: Int): ResultDto<ArticlePageDto> = request {
        LogUtils.d(TAG, "getArticleList")
        service.getArticle(page = page, pageSize = pageSize)
    }
}
