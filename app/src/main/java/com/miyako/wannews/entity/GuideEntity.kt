package com.miyako.wannews.entity

/**
 * @Description
 * @Author Miyako
 * @Date 2021-10-10-0010
 */
open class GuideEntity(
    val id: Int,
    val name: String)

class GuideClassEntity(id: Int, name: String): GuideEntity(id, name)

class SystemTreeEntity(id: Int, name: String): GuideEntity(id, name)

