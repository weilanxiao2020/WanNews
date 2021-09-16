package com.miyako.wannews.data.datasource.factory

/**
 * @Description Jetpack Paging组件标识的数据源仓库接口
 * @Author Miyako
 * @Date 2021-09-08-0008
 */
interface IBaseDataSourceFactory {
    val TAG: String
        get() = this::class.java.simpleName
}