package com.miyako.wannews.network

import com.google.gson.annotations.SerializedName

/**
 * @Description 网络请求获取的接口数据实体类
 * @Author Miyako
 * @Date 2021-08-30-0030
 */
// 首页置顶文章
data class IndexTopDto(
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
    val tags: List<IndexTopTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)
data class IndexTopTag(
    val name: String,
    val url: String
)

data class ArticlePageDto(
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Article(
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
    val tags: List<ArticleTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class ArticleTag(
    val name: String,
    val url: String
)