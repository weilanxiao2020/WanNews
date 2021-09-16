package com.miyako.wannews.ui.main.contentPages

import androidx.lifecycle.*
import androidx.paging.*
import com.miyako.util.LogUtils
import com.miyako.wannews.base.BaseViewModel
import com.miyako.wannews.data.repository.Repository
import com.miyako.wannews.entity.IndexArticleEntity
import com.miyako.wannews.entity.ProjectClassEntity
import com.miyako.wannews.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @Description ContentPage的ViewModel
 * @Author Miyako
 * @Date 2021-08-27-0027
 */
class ContentPagesViewModel: BaseViewModel() {

    val projectClassLiveData = MutableLiveData<List<ProjectClassEntity>>()

    val indexTopArticleLiveData = MutableLiveData<List<IndexArticleEntity>>()

    fun getIndexTopArticle(): Flow<List<IndexArticleEntity>> {
        launchUI {
            indexTopArticleLiveData.value = Repository.getIndexTopArticle()
        }
        return indexTopArticleLiveData.asFlow()
    }

    fun getIndexArticlePage(): Flow<PagingData<IndexArticleEntity>> {
        return Repository.getIndexArticlePageData().cachedIn(viewModelScope)
    }

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