package com.miyako.wannews.data.repository

import com.miyako.util.LogUtils
import com.miyako.wannews.network.common.HttpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class ProjectRepository : IBaseRepository {

    private val service = HttpRequest.getInstance().getProjectService()

    suspend fun getProjectClasses() = withContext(Dispatchers.IO) {
        try {
            LogUtils.d(TAG, "getProjectClasses")
            service.getProjectClass()
        } catch (e: Exception) {
            LogUtils.e(TAG, "getProjectClasses: ${e.message}")
            null
        }
    }

    suspend fun getProjectList(page: Int, pageSize: Int, classId: Int) = withContext(Dispatchers.IO) {
        try {
            service.getProjectList(page = page, pageSize = pageSize, classId = classId)
        } catch (e: Exception) {
            LogUtils.e(TAG, "getProjectList: ${e.message}")
            null
        }
    }

}
