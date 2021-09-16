package com.miyako.wannews.data.datasource.factory

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.miyako.wannews.data.datasource.NewsEntityDataSource
import com.miyako.wannews.entity.IndexArticleEntity


/**
 * @Description
 * @Author Miyako
 * @Date 2021-09-06-0006
 */
class NewsEntityDataSourceFactory : DataSource.Factory<Int, IndexArticleEntity>(), IBaseDataSourceFactory {

    private val mSourceLiveData: MutableLiveData<NewsEntityDataSource> = MutableLiveData<NewsEntityDataSource>()
    private var mLastDataSource: NewsEntityDataSource? = null
    override fun create(): DataSource<Int, IndexArticleEntity> {
        TODO("Not yet implemented")
    }

    // private val netWorkStatus: MutableLiveData<NetWorkStatus>? = null

    // override fun create(): DataSource<Int, NewsModel> {
    //     lastDataSource = NewsModelDataSource()
    //     sourceLiveData.postValue(lastDataSource)
    //     return lastDataSource as NewsModelDataSource;
    // }
}