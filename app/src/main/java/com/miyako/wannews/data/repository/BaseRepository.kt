package com.miyako.wannews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miyako.architecture.domain.request.BaseRequest
import com.miyako.wannews.data.datasource.NewsEntityDataSource
import com.miyako.wannews.data.datasource.ProjectPageDataSource
import com.miyako.wannews.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * @Description Jetpack Paging组件标识的存储接口
 * @Author Miyako
 * @Date 2021-09-08-0008
 */
abstract class BaseRepository {
    val TAG: String
        get() = this::class.java.simpleName

    inline fun <reified T> requestExecute(request: BaseRequest): T? {
        return if (request is T) {
            return request
        } else {
            null
        }
    }

    // fun getRequestImpl(): BaseRequest

    // suspend fun <T : Any> request(call: suspend () -> ResultDto<T>): ResultDto<T> {
    //     return withContext(Dispatchers.IO) {
    //         call.invoke()
    //     }.apply {
    //         com.miyako.architecture.util.LogUtils.d(TAG, "接口返回数据-----> ${this.toString()}")
    //         //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
    //         //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理
    //         when (errorCode) {
    //             //一般0和200是请求成功，直接返回数据
    //             0, 200 -> this
    //             // 100, 401 -> throw TokenInvalidException(errorMsg)
    //             // 403 -> throw NoPermissionsException(errorMsg)
    //             // 404 -> throw NotFoundException(errorMsg)
    //             // 500 -> throw InterfaceErrException(errorMsg)
    //             else -> {
    //                 com.miyako.architecture.util.LogUtils.e(TAG, "网络请求错误：$errorMsg")
    //                 throw Exception(errorMsg)
    //             }
    //         }
    //     }
    // }
}

object Repository {

    val TAG = Repository::class.java.simpleName

    private val indexRepository = HomeRepository()
    private val projectRepository = ProjectRepository()
    private val guideRepository = GuideRepository()

    /**
     * 首页置顶文章
     */
    suspend fun getHomeTopArticle(): List<HomeArticleEntity> {
        return try {
            val result = indexRepository.getHomeTopArticle()
            val list = mutableListOf<HomeArticleEntity>()
            result.responseData?.forEach { it ->
                val entity = HomeArticleEntity(
                    title = it.title, author = it.author, publishTime = it.publishTime, link = it.link,
                    tags = it.tags.asSequence().map { IndexArticleTag(it.name, it.url) }.toList()
                )
                list.add(entity)
            }
            list
        } catch (e: Exception) {
            listOf<HomeArticleEntity>()
        }
    }

    /**
     * 首页文章分页
     */
    fun getHomeArticlePageData(): Flow<PagingData<HomeArticleEntity>> {
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

    /**
     * 获取导航分类
     */
    suspend fun getGuideClass(): List<GuideClassEntity> {
        return try {
            val result = guideRepository.getGuideClass()
            val list = mutableListOf<GuideClassEntity>()
            result.responseData?.forEach {
                val entity = GuideClassEntity(id = it.cid, name = it.name)
                list.add(entity)
            }
            list
        } catch (e: Exception) {
            listOf<GuideClassEntity>()
        }
    }

    /**
     * 获取分类
     */
    suspend fun getSystemTree(): List<SystemTreeEntity> {
        return try {
            // val result = guideRepository.getSystemTree()
            val list = mutableListOf<SystemTreeEntity>()
            // result.resultData.forEach {
            //     val entity = SystemTreeEntity(id = it.id, name = it.name)
            //     list.add(entity)
            // }
            list
        } catch (e: Exception) {
            com.miyako.architecture.util.LogUtils.e(TAG, e.toString())
            listOf<SystemTreeEntity>()
        }
    }

    /**
     * 获取项目分类
     */
    suspend fun getProjectClass(): List<ProjectClassEntity> {
        return try {
            val result = projectRepository.getProjectClasses()
            val list = mutableListOf<ProjectClassEntity>()
            result.responseData?.forEach {
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

    /**
     * 获取指定项目分类
     */
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