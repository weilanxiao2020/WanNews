package com.miyako.wannews.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miyako.util.LogUtils
import com.miyako.wannews.data.repository.IndexRepository
import com.miyako.wannews.entity.IndexArticleEntity
import com.miyako.wannews.entity.IndexArticleTag
import com.miyako.wannews.network.ArticlePageDto
import kotlin.random.Random

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class NewsEntityDataSource(
    private val repo: IndexRepository
    ): PagingSource<Int, IndexArticleEntity>(), IBaseDataSource<List<IndexArticleEntity>> {

    // 用于刷新
    override fun getRefreshKey(state: PagingState<Int, IndexArticleEntity>): Int? {
        val idx = Random(System.currentTimeMillis()).nextInt() % 10
        LogUtils.d(TAG, "get refresh key $idx")
        return idx
    }

    // 用于获取
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IndexArticleEntity> {
        val loadPage = params.key ?: 0
        LogUtils.i(TAG, "current page = $loadPage")
        return try {
            val result = repo.getArticleList(loadPage, params.loadSize)
            LogUtils.d(TAG, "ssss:${result?:"asdasd"}")
            LoadResult.Page(
                data = convertEntity(result.resultData),
                prevKey = null,
                nextKey = if (result.resultData.over) null else result.resultData.curPage
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = Throwable())
        }
    }

    override fun convertEntity(data: Any): List<IndexArticleEntity> {
        val list = mutableListOf<IndexArticleEntity>()
        (data as ArticlePageDto).let { it ->
            it.datas.forEach {
                LogUtils.d(TAG, "item: ${it.title}-${it.author}")
                list.add(IndexArticleEntity(it.title, author = if ("" == it.author) {
                    "佚名"
                } else {
                    it.author
                }, publishTime = it.publishTime, link = it.link, it.tags.asSequence().map { IndexArticleTag(it.name, it.url) }.toList()))
            }
            LogUtils.d(TAG, "list size:${list.size}")
        }
        return list
    }
}