package com.fy.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<TType : ViewBinding>(private val type: TType): RecyclerView.ViewHolder(type.root) {

    fun getBinding(): TType {
        return this.type
    }
}