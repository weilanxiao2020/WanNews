package com.miyako.wannews.network.api

import com.miyako.wannews.network.ArticlePageDto
import com.miyako.wannews.network.common.IDataService
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IArticleService : IDataService {

    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page") page:Int): ArticlePageDto

    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page") page:Int, @Query("page_size") pageSize: Int): ArticlePageDto

}