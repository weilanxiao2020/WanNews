package com.miyako.wannews.network.api

import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.common.IDataService
import com.miyako.wannews.network.dto.GuideDto
import com.miyako.wannews.network.dto.ResultDto
import com.miyako.wannews.network.dto.SystemTreeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IGuideService : IDataService {

    @GET("/navi/json")
    suspend fun getGuide(): ResultDto<List<GuideDto>>

    @GET("/tree/json")
    suspend fun getSystem(): ResultDto<List<SystemTreeDto>>

}