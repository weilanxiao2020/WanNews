package com.miyako.wannews.ui.main

import com.miyako.wannews.R

/**
 * @Description
 * @Author Miyako
 * @Date 2021-08-22-0022
 */
sealed class NavigationItem(var route: String, val normalIcon: Int, val selectIcon: Int, var title: String) {
    object News: NavigationItem("news", R.mipmap.ic_rb_bottom_news, R.mipmap.ic_rb_bottom_news_selected, "News")
    object Project: NavigationItem("project", R.mipmap.ic_rb_bottom_project, R.mipmap.ic_rb_bottom_project_selected, "Project")
    object Part: NavigationItem("part", R.mipmap.ic_rb_bottom_part, R.mipmap.ic_rb_bottom_part_selected, "Part")
    object Public: NavigationItem("public", R.mipmap.ic_rb_bottom_public, R.mipmap.ic_rb_bottom_public_selected, "Public")
    object Mine: NavigationItem("mine", R.mipmap.ic_rb_bottom_mine, R.mipmap.ic_rb_bottom_mine_selected, "Mine")
}