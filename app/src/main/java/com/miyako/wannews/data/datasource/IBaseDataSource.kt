package com.miyako.wannews.data.datasource

/**
 * @Description Jetpack Paging组件标识的数据源接口
 * @Author Miyako
 * @Date 2021-09-08-0008
 */
interface IBaseDataSource<T> {
    val TAG: String
        get() = this::class.java.simpleName

    // 数据源数据转换为Paging使用的实体数据
    fun convertEntity(data: Any): T
}