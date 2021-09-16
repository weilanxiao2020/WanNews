package com.miyako.wannews.ui.main.contentPages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.*
import com.miyako.util.LogUtils
import com.miyako.wannews.base.BaseViewModel
import com.miyako.wannews.data.repository.Repository
import com.miyako.wannews.entity.NewsEntity
import com.miyako.wannews.entity.ProjectClassEntity
import com.miyako.wannews.entity.ProjectEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @Description ContentPage的ViewModel
 * @Author Miyako
 * @Date 2021-08-27-0027
 */
class ContentPagesViewModel: BaseViewModel() {

    val projectClassLiveData = MutableLiveData<List<ProjectClassEntity>>()

    fun getNewsPage(): Flow<PagingData<NewsEntity>> {
        return Repository.getNewsPageData().cachedIn(viewModelScope)
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