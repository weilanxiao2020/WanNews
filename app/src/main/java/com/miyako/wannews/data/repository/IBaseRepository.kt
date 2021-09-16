package com.miyako.wannews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miyako.util.LogUtils
import com.miyako.wannews.data.datasource.NewsEntityDataSource
import com.miyako.wannews.data.datasource.ProjectPageDataSource
import com.miyako.wannews.entity.*
import com.miyako.wannews.network.dto.ResultDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * @Description Jetpack Paging组件标识的存储接口
 * @Author Miyako
 * @Date 2021-09-08-0008
 */
interface IBaseRepository {
    val TAG: String
        get() = this::class.java.simpleName

    suspend fun <T : Any> request(call: suspend () -> ResultDto<T>): ResultDto<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            LogUtils.d("接口返回数据----->", this.toString())
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理
            when (errorCode) {
                //一般0和200是请求成功，直接返回数据
                0, 200 -> this
                // 100, 401 -> throw TokenInvalidException(errorMsg)
                // 403 -> throw NoPermissionsException(errorMsg)
                // 404 -> throw NotFoundException(errorMsg)
                // 500 -> throw InterfaceErrException(errorMsg)
                else -> throw Exception(errorMsg)
            }
        }
    }
}

object Repository {

    val TAG = Repository::class.java.simpleName

    private val indexRepository = IndexRepository()
    private val projectRepository = ProjectRepository()

    suspend fun getIndexTopArticle(): List<IndexArticleEntity> {
        return try {
            val result = indexRepository.getIndexTopArticle()
            val list = mutableListOf<IndexArticleEntity>()
            result.resultData.forEach { it ->
                val entity = IndexArticleEntity(
                    title = it.title, author = it.author, publishTime = it.publishTime, link = it.link,
                    tags = it.tags.asSequence().map { IndexArticleTag(it.name, it.url) }.toList()
                )
                list.add(entity)
            }
            list
        } catch (e: Exception) {
            listOf<IndexArticleEntity>()
        }
    }

    fun getIndexArticlePageData(): Flow<PagingData<IndexArticleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, //一次加载20条
                prefetchDistance = 2,  //距离结尾处几个时候开始加载下一页
                initialLoadSize = 20,
                enablePlaceholders = true),
            pagingSourceFactory = {
                NewsEntityDataSource(repo = indexRepository)
            }
        ).flow
    }

    suspend fun getProjectClass(): List<ProjectClassEntity> {
        return try {
            val result = projectRepository.getProjectClasses()
            val list = mutableListOf<ProjectClassEntity>()
            result.resultData.forEach {
                    val entity = ProjectClassEntity(children = it.children,
                        courseId = it.courseId, id = it.id, name = it.name, order = it.order,
                        parentChapterId = it.parentChapterId, userControlSetTop = it.userControlSetTop, visible = it.visible)
                    list.add(entity)
                }
            list
        } catch (e: Exception) {
            listOf<ProjectClassEntity>()
        }
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