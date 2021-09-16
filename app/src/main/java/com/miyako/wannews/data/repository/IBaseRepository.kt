package com.miyako.wannews.data.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.miyako.util.LogUtils
import com.miyako.wannews.data.datasource.NewsEntityDataSource
import com.miyako.wannews.data.datasource.ProjectPageDataSource
import com.miyako.wannews.entity.NewsEntity
import com.miyako.wannews.entity.ProjectClassEntity
import com.miyako.wannews.entity.ProjectEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @Description Jetpack Paging组件标识的存储接口
 * @Author Miyako
 * @Date 2021-09-08-0008
 */
interface IBaseRepository {
    val TAG: String
        get() = this::class.java.simpleName
}

object Repository {

    val TAG = Repository::class.java.simpleName

    private val newsRepository = NewsRepository()
    private val projectRepository = ProjectRepository()

    fun getNewsPageData(): Flow<PagingData<NewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, //一次加载20条
                prefetchDistance = 2,  //距离结尾处几个时候开始加载下一页
                initialLoadSize = 20,
                enablePlaceholders = true),
            pagingSourceFactory = {
                NewsEntityDataSource(repo = newsRepository)
            }
        ).flow
    }

    suspend fun getProjectClass(): List<ProjectClassEntity> {
        val result = projectRepository.getProjectClasses()
        val list = mutableListOf<ProjectClassEntity>()
        result?.apply {
            // LogUtils.d(TAG, "class size:${result.projectClassList.size}")

            projectClassList.forEach {
                val entity = ProjectClassEntity(children = it.children,
                    courseId = it.courseId, id = it.id, name = it.name, order = it.order,
                    parentChapterId = it.parentChapterId, userControlSetTop = it.userControlSetTop, visible = it.visible)
                list.add(entity)
            }
        }
        return list
    }

    fun getProjectPage(classId: Int): Flow<PagingData<ProjectEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, //一次加载20条
                prefetchDistance = 2,  //距离结尾处几个时候开始加载下一页
                initialLoadSize = 20,
                enablePlaceholders = true),
            pagingSourceFactory = {
                ProjectPageDataSource(repo = projectRepository, classId)
            }
        ).flow
    }
}