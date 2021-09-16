package com.miyako.wannews.entity

/**
 * @Description APP中使用的数据实体表达类
 * @Author Miyako
 * @Date 2021-08-30-0030
 */
data class NewsEntity(
    val title: String,
    val author: String,
    val publishTime: Long,
    val link: String) {
}

data class NewsEntityList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NewsEntity>
) {

}
