package com.miyako.wannews.data.repository

import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.domain.ResultSource
import com.miyako.architecture.domain.request.BaseCoroutinesRequest
import com.miyako.architecture.domain.request.BaseRequest
import com.miyako.architecture.domain.request.HttpCoroutinesRequest
import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import com.miyako.wannews.network.ProjectClassDto
import com.miyako.wannews.network.ProjectPageDto
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.ResultDto

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class ProjectRepository(
    private var requestImpl: BaseRequest = HttpCoroutinesRequest()
) : BaseRepository() {


    private val service = HttpRequest.getInstance().getProjectService()

    suspend fun getProjectClasses(): ResultData<List<ProjectClassDto>> {
        return if (requestExecute<BaseCoroutinesRequest>(requestImpl) != null) {
            LogUtils.d(TAG, "getProjectClasses")
            service.getProjectClass()
        } else {
            ResultData.empty()
        }
    }

    suspend fun getProjectList(page: Int, pageSize: Int, classId: Int): ResultData<ProjectPageDto> {
        return if (requestExecute<BaseCoroutinesRequest>(requestImpl) != null) {
            LogUtils.d(TAG, "getProjectClasses")
            service.getProjectList(page = page, pageSize = pageSize, classId = classId)
        } else {
            ResultData.empty()
        }
    }

}
