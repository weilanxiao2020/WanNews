package com.miyako.wannews.network.dto

import com.google.gson.annotations.SerializedName

/**
 * @Description
 * @Author Miyako
 * @Date 2021-10-10-0010
 */
data class GuideDto(
    @SerializedName("articles")
    val guideClassDto: List<GuideClassDto>,
    val cid: Int,
    val name: String
)

data class GuideClassDto(
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
    val shareDate: Any,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)