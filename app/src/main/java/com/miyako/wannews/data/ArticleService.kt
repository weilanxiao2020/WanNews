package com.miyako.wannews.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService : IDataService {


    @GET("/article/list/{index}/json")
    fun getArticle(@Path("index") idx:Int): Call<ArticleDto>
}