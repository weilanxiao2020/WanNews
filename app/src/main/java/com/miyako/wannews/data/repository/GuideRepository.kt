package com.miyako.wannews.data.repository

import com.miyako.architecture.domain.ResponseStatus
import com.miyako.architecture.domain.ResultSource
import com.miyako.architecture.domain.request.BaseCoroutinesRequest
import com.miyako.architecture.domain.request.BaseRequest
import com.miyako.architecture.domain.request.HttpCoroutinesRequest
import com.miyako.architecture.domain.result.ResultData
import com.miyako.architecture.util.LogUtils
import com.miyako.wannews.network.common.HttpRequest
import com.miyako.wannews.network.dto.GuideDto

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class GuideRepository(
    private var requestImpl: BaseRequest = HttpCoroutinesRequest()
): BaseRepository() {

    private val service = HttpRequest.getInstance().getGuideService()

    suspend fun getGuideClass(): ResultData<List<GuideDto>> {
        return if (requestExecute<BaseCoroutinesRequest>(requestImpl) != null) {
            LogUtils.d(TAG, "getGuideClass")
            service.getGuide()
        } else {
            ResultData.emptyList()
        }
    }

    // suspend fun getSystemTree(): ResultDto<List<SystemTreeDto>> = request {
    //     com.miyako.architecture.util.LogUtils.d(TAG, "getSystemTree")
    //     service.getSystem()
    // }

}
