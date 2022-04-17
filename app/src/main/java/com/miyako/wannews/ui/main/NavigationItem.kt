package com.miyako.wannews.ui.main

import com.miyako.architecture.util.Utils
import com.miyako.wannews.R
import com.miyako.wannews.base.App

/**
 * @Description
 * @Author Miyako
 * @Date 2021-08-22-0022
 */
sealed class NavigationItem(var route: String, val normalIcon: Int, val selectIcon: Int, var title: String) {
    object Index: NavigationItem("index",
        R.mipmap.ic_rb_bottom_news, R.mipmap.ic_rb_bottom_news_selected,
        Utils.getApp().getString(R.string.rb_bottom_index_label))
    object Guide: NavigationItem("guide",
        R.mipmap.ic_rb_bottom_project, R.mipmap.ic_rb_bottom_project_selected,
        Utils.getApp().getString(R.string.rb_bottom_guide_label))
    object Public: NavigationItem("public",
        R.mipmap.ic_rb_bottom_public, R.mipmap.ic_rb_bottom_public_selected,
        Utils.getApp().getString(R.string.rb_bottom_public_label))
    object Question: NavigationItem("question",
        R.mipmap.ic_rb_bottom_part, R.mipmap.ic_rb_bottom_part_selected,
        Utils.getApp().getString(R.string.rb_bottom_question_label))
    object Mine: NavigationItem("mine",
        R.mipmap.ic_rb_bottom_mine, R.mipmap.ic_rb_bottom_mine_selected,
        Utils.getApp().getString(R.string.rb_bottom_mine_label))
}