package com.miyako.wannews.ui.main.HomeScreen

import androidx.lifecycle.*
import androidx.paging.*
import com.miyako.util.LogUtils
import com.miyako.wannews.base.BaseViewModel
import com.miyako.wannews.data.repository.Repository
import com.miyako.wannews.entity.HomeArticleEntity
import com.miyako.wannews.entity.ProjectClassEntity
import com.miyako.wannews.entity.ProjectEntity
import com.miyako.wannews.ui.main.contentPages.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @Description HomeScreen的ViewModel
 * @Author Miyako
 * @Date 2021-08-27-0027
 */
class HomeScreenViewModel: BaseViewModel() {

    public val projectClassLiveData = MutableLiveData<List<ProjectClassEntity>>()

    public val indexTopArticleLiveData = MutableLiveData<List<HomeArticleEntity>>()

    /**
     * 获取置顶文章
     */
    fun getHomeTopArticle(): Flow<List<HomeArticleEntity>> {
        launchUI {
            indexTopArticleLiveData.value = Repository.getHomeTopArticle()
        }
        return indexTopArticleLiveData.asFlow()
    }

    /**
     * 获取首页文章分页
     */
    fun getHomeArticlePage(): Flow<PagingData<HomeArticleEntity>> {
        return Repository.getHomeArticlePageData().cachedIn(viewModelScope)
    }

    /**
     * 获取首页文章分页
     */
    fun getProjectClass(): Flow<List<ProjectClassEntity>> {
        LogUtils.e(TAG, "......")
        viewModelScope.launch {
            val result = Repository.getProjectClass()
            projectClassLiveData.value = result
        }
        return projectClassLiveData.asFlow()
    }

    fun getProjectPage(classId: Int): Flow<PagingData<ProjectEntity>> {
        return Repository.getProjectPage(classId).cachedIn(viewModelScope)
    }
}