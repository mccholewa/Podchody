package com.podchody.common

/**
 * Created by Misiu on 09.01.2018.
 */

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class DataBoundViewHolder<T : Any, out V : ViewDataBinding>
private constructor(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup, factory: (LayoutInflater, ViewGroup, Boolean) -> V) :
            this(factory(LayoutInflater.from(parent.context), parent, false))

    lateinit var item: T

    abstract fun bind(t: T)
}