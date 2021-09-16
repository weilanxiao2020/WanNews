package com.miyako.wannews.entity

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-14-0014
 */
data class ProjectClassEntity(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
data class ProjectEntity(
    val id: Int,
    val title: String,
    val author: String,
    val link: String
)