package com.miyako.wannews.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miyako.architecture.util.LogUtils
import com.miyako.wannews.data.repository.HomeRepository
import com.miyako.wannews.entity.HomeArticleEntity
import com.miyako.wannews.entity.IndexArticleTag
import com.miyako.wannews.network.ArticlePageDto
import kotlin.random.Random

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class NewsEntityDataSource(
    private val repo: HomeRepository
    ): PagingSource<Int, HomeArticleEntity>(), IBaseDataSource<List<HomeArticleEntity>> {

    // 用于刷新
    override fun getRefreshKey(state: PagingState<Int, HomeArticleEntity>): Int? {
        val idx = Random(System.currentTimeMillis()).nextInt() % 10
        LogUtils.d(TAG, "get refresh key $idx")
        return idx
    }

    // 用于获取
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeArticleEntity> {
        val loadPage = params.key ?: 0
        LogUtils.i(TAG, "current page = $loadPage")
        return try {
            val result = repo.getArticleList(loadPage, params.loadSize)
            LogUtils.d(TAG, "ssss:${result?:"asdasd"}")
            LoadResult.Page(
                data = convertEntity(result.responseData!!),
                prevKey = null,
                nextKey = if (result.responseData!!.over) null else result.responseData!!.curPage
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = Throwable())
        }
    }

    override fun convertEntity(data: Any): List<HomeArticleEntity> {
        val list = mutableListOf<HomeArticleEntity>()
        (data as ArticlePageDto).let { it ->
            it.datas.forEach {
                LogUtils.d(TAG, "item: ${it.title}-${it.author}")
                list.add(HomeArticleEntity(it.title, author = if ("" == it.author) {
                    "佚名"
                } else {
                    it.author
                }, publishTime = it.publishTime,
                    link = it.link,
                    chapterName = it.chapterName,
                    tags = it.tags.asSequence().map { IndexArticleTag(it.name, it.url) }.toList()))
            }
            LogUtils.d(TAG, "list size:${list.size}")
        }
        return list
    }
}