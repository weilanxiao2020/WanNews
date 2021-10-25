package com.miyako.wannews.network.dto

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-14-0014
 */
data class SystemTreeDto(
    val children: List<SystemNodeDto>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class SystemNodeDto(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)