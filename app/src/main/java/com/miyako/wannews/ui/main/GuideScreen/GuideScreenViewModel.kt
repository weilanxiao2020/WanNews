package com.miyako.wannews.ui.main.GuideScreen

import androidx.lifecycle.*
import androidx.paging.*
import com.miyako.util.LogUtils
import com.miyako.wannews.base.BaseViewModel
import com.miyako.wannews.data.repository.Repository
import com.miyako.wannews.entity.GuideClassEntity
import com.miyako.wannews.entity.ProjectClassEntity
import com.miyako.wannews.entity.ProjectEntity
import com.miyako.wannews.entity.SystemTreeEntity
import com.miyako.wannews.ui.main.contentPages.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @Description GuideScreen的ViewModel
 * @Author Miyako
 * @Date 2021-08-27-0027
 */
class GuideScreenViewModel: BaseViewModel() {

    // 多个接口合并

    public val guideClassLiveData = MutableLiveData<List<GuideClassEntity>>()
    public val systemTreeLiveData = MutableLiveData<List<SystemTreeEntity>>()

    /**
     * 获取导航分类数据
     */
    fun getGuide(): Flow<List<GuideClassEntity>> {
        LogUtils.e(TAG, "getGuide")
        viewModelScope.launch {
            val result = Repository.getGuideClass()
            guideClassLiveData.value = result
        }
        return guideClassLiveData.asFlow()
    }

    fun getSystem(): Flow<List<SystemTreeEntity>> {
        LogUtils.e(TAG, "getSystem")
        viewModelScope.launch {
            val result = Repository.getSystemTree()
            systemTreeLiveData.value = result
        }
        return systemTreeLiveData.asFlow()
    }
}