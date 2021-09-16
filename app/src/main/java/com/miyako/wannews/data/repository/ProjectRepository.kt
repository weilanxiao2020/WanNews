package com.miyako.wannews.data.repository

import com.miyako.util.LogUtils
import com.miyako.wannews.network.ProjectClassDto
import com.miyako.wannews.network.ProjectPageDto
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.ResultDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class ProjectRepository : IBaseRepository {

    private val service = HttpRequest.getInstance().getProjectService()

    suspend fun getProjectClasses(): ResultDto<List<ProjectClassDto>> = request {
        LogUtils.d(TAG, "getProjectClasses")
        service.getProjectClass()
    }

    suspend fun getProjectList(page: Int, pageSize: Int, classId: Int): ResultDto<ProjectPageDto> = request {
        service.getProjectList(page = page, pageSize = pageSize, classId = classId)
    }

}
