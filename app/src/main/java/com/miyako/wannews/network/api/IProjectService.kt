package com.miyako.wannews.network.api

import com.miyako.architecture.domain.result.ResultData
import com.miyako.wannews.network.ProjectClassDto
import com.miyako.wannews.network.ProjectPageDto
import com.miyako.wannews.network.common.IDataService
import com.miyako.wannews.network.dto.ResultDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-14-0014
 */
interface IProjectService: IDataService {

    @GET("/project/tree/json")
    suspend fun getProjectClass(): ResultData<List<ProjectClassDto>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int,
                               @Query("page_size") pageSize:Int,
                               @Query("cid") classId: Int): ResultData<ProjectPageDto>
}