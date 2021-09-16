package com.miyako.wannews.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miyako.util.LogUtils
import com.miyako.wannews.data.repository.NewsRepository
import com.miyako.wannews.entity.NewsEntity
import com.miyako.wannews.network.ArticlePageDto
import kotlin.random.Random

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class NewsEntityDataSource(
    private val repo: NewsRepository
    ): PagingSource<Int, NewsEntity>(), IBaseDataSource<List<NewsEntity>> {

    // 用于刷新
    override fun getRefreshKey(state: PagingState<Int, NewsEntity>): Int? {
        val idx = Random(System.currentTimeMillis()).nextInt() % 10
        LogUtils.d(TAG, "get refresh key $idx")
        return idx
    }

    // 用于获取
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsEntity> {
        val loadPage = params.key ?: 0
        LogUtils.i(TAG, "current page = $loadPage")
        val result = repo.getArticleList(loadPage, params.loadSize)
        return if (result != null) {
            return LoadResult.Page(
                data = convertEntity(result),
                prevKey = null,
                nextKey = if (result.articlePage.over) null else result.articlePage.curPage
            )
        } else {
            LoadResult.Error(throwable = Throwable())
        }
    }

    override fun convertEntity(data: Any) : List<NewsEntity> {
        val list = mutableListOf<NewsEntity>()
        (data as ArticlePageDto).let { it ->
            it.articlePage.datas.forEach {
                LogUtils.d(TAG, "item: ${it.title}-${it.author}")
                list.add(NewsEntity(it.title, author = if ("" == it.author) {
                    "佚名"
                } else {
                    it.author
                }, publishTime = it.publishTime, link = it.link))
            }
            LogUtils.d(TAG, "list size:${list.size}")
        }
        return list
    }
}