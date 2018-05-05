package com.podchody.ui.common

/**
 * Created by Misiu on 10.01.2018.
 */

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.podchody.modle.GameFb
import com.podchody.ui.lobby.LobbyViewHolder
import timber.log.Timber

class DataBoundListAdapter<T : Any, V : ViewDataBinding>(
        private var factory: (ViewGroup) -> DataBoundViewHolder<T, V>
) : RecyclerView.Adapter<DataBoundViewHolder<T, V>>() {

    private var items: List<T> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<T, V> = factory(parent)

    override fun onBindViewHolder(holder: DataBoundViewHolder<T, V>, position: Int) {
        val item = items[position]
        holder.item = item
        holder.bind(item)
        holder.binding.executePendingBindings()
    }

    fun replace(update: List<T>) {
        this.items = update
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}