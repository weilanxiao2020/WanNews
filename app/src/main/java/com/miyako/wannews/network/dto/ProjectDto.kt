package com.miyako.wannews.network

import com.google.gson.annotations.SerializedName

/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-14-0014
 */
data class ProjectClassDto(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class ProjectPageDto(
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<Project>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Project(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<ProjectTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class ProjectTag(
    val name: String,
    val url: String
)

