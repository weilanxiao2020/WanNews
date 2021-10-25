package com.miyako.wannews.entity

/**
 * @Description APP中使用的数据实体表达类
 * @Author Miyako
 * @Date 2021-08-30-0030
 */
data class HomeArticleEntity(
    val title: String,
    val author: String,
    val publishTime: Long,
    val link: String,
    val tags: List<IndexArticleTag>)

data class IndexArticleTag(
    val name: String,
    val url: String
)