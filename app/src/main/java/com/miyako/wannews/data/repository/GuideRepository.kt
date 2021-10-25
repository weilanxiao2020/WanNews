package com.miyako.wannews.data.repository

import com.miyako.util.LogUtils
import com.miyako.wannews.network.ProjectClassDto
import com.miyako.wannews.network.ProjectPageDto
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.GuideDto
import com.miyako.wannews.network.dto.ResultDto
import com.miyako.wannews.network.dto.SystemTreeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class GuideRepository : IBaseRepository {

    private val service = HttpRequest.getInstance().getGuideService()

    suspend fun getGuideClass(): ResultDto<List<GuideDto>> = request {
        LogUtils.d(TAG, "getGuideClass")
        service.getGuide()
    }

    suspend fun getSystemTree(): ResultDto<List<SystemTreeDto>> = request {
        LogUtils.d(TAG, "getSystemTree")
        service.getSystem()
    }

}
