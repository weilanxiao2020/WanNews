package com.miyako.wannews.ui

import android.os.Bundle
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import com.miyako.wannews.base.BaseActivity
import com.miyako.wannews.data.ArticleDto
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : BaseActivity() {

    override fun getLayoutId() : Int{
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        switchBottomBar(0)
        rb_news_page.setOnClickListener { switchBottomBar(0) }
        rb_project_page.setOnClickListener { switchBottomBar(1) }
        rb_part_page.setOnClickListener { switchBottomBar(2) }
        rb_public_page.setOnClickListener { switchBottomBar(3) }
        rb_mine_page.setOnClickListener { switchBottomBar(4) }
    }


    var idx = 0
    fun switchBottomBar(idx: Int) {
        this.idx = idx
        LogUtils.d(TAG, "底部导航栏切换：${idx}")
        when (idx) {
            4 -> {
                rb_mine_page.isChecked = true
                getArticle(4)
            }
            3 -> {
                rb_public_page.isChecked = true
                getArticle(3)
            }
            2 -> {
                rb_part_page.isChecked = true
                getArticle(2)
            }
            1 -> {
                rb_project_page.isChecked = true
                getArticle(1)
            }
            else -> {
                rb_news_page.isChecked = true
                getArticle(0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        requestData()
    }

    fun getArticle(idx :Int) {
        val repos: Call<ArticleDto> = httpRequest.getArticleService().getArticle(idx)
        LogUtils.d(TAG, "retrofit")
        repos.enqueue(object : retrofit2.Callback<ArticleDto>{
            override fun onFailure(call: Call<ArticleDto>, t: Throwable) {
                LogUtils.e(TAG, "error")
                LogUtils.d(TAG, call.toString())
            }

            override fun onResponse(call: Call<ArticleDto>, response: Response<ArticleDto>) {
                LogUtils.d(TAG, "success")
                LogUtils.d(TAG, response.body().toString())
            }

        })
    }

    override fun requestData() {
        getArticle(0)
    }
}