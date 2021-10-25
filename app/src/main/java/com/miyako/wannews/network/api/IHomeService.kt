package com.miyako.wannews.network.api

import com.miyako.wannews.network.IndexTopDto
import com.miyako.wannews.network.common.IDataService
import com.miyako.wannews.network.dto.ResultDto
import retrofit2.http.GET

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-16-0016
 */
interface IHomeService: IDataService {

    @GET("/article/top/json")
    suspend fun getHomeTopArticle(): ResultDto<List<IndexTopDto>>
}