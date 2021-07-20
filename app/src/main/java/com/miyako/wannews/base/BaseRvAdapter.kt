package com.miyako.wannews.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<T>
    (protected val mContext: Context, protected val mData:MutableList<T> = mutableListOf()) :
    RecyclerView.Adapter<BaseRvViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder {
        val view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false)
        return getViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType()
    }

    override fun onBindViewHolder(holder: BaseRvViewHolder, position: Int) {
        bindView(holder, position)
    }


    abstract fun getLayoutId() : Int

    abstract fun getViewType() : Int

    abstract fun getViewHolder(inflate: View) : BaseRvViewHolder

    abstract fun bindView(holder: BaseRvViewHolder, position: Int)
}