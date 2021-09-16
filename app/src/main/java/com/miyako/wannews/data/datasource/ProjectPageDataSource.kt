package com.miyako.wannews.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miyako.util.LogUtils
import com.miyako.wannews.data.repository.ProjectRepository
import com.miyako.wannews.entity.ProjectEntity
import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.ProjectPageDto
import kotlin.random.Random

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class ProjectPageDataSource(
    private val repo: ProjectRepository, val classid: Int
    ): PagingSource<Int, ProjectEntity>(), IBaseDataSource<List<ProjectEntity>> {

    // 用于刷新
    override fun getRefreshKey(state: PagingState<Int, ProjectEntity>): Int? {
        val idx = Random(System.currentTimeMillis()).nextInt() % 10
        LogUtils.d(TAG, "get refresh key $idx")
        return idx
    }

    // 用于获取
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectEntity> {
        val loadPage = params.key ?: 0
        LogUtils.i(TAG, "current page = $loadPage")
        val result = repo.getProjectList(loadPage, params.loadSize, classid)
        return if (result != null) {
            return LoadResult.Page(
                data = convertEntity(result.resultData),
                prevKey = null,
                nextKey = if (result.resultData.over) null else result.resultData.curPage + 1
            )
        } else {
            LoadResult.Error(throwable = Throwable())
        }
    }

    override fun convertEntity(data: Any) : List<ProjectEntity> {
        val list = mutableListOf<ProjectEntity>()
        (data as ProjectPageDto).let { it ->
            it.datas.forEach {
                LogUtils.d(TAG, "item: ${it.title}-${it.author}")
                list.add(ProjectEntity(it.id, author = if ("" == it.author) {
                    "佚名"
                } else {
                    it.author
                }, title = it.title, link = it.link))
            }
            LogUtils.d(TAG, "list size:${list.size}")
        }
        return list
    }
}