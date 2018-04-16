package com.podchody.ui.lobby

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.podchody.databinding.UserItemBinding
import com.podchody.ui.common.DataBoundViewHolder
import com.podchody.vo.User

/**
 * Created by Misiu on 12.04.2018.
 */

class UserViewHolder(parent: ViewGroup, viewModel: LobbyViewModel) :
        DataBoundViewHolder<User, UserItemBinding>(parent, UserItemBinding::inflate) {
    init {
        //binding.showFullName = true
        binding.root.setOnClickListener {

        }
    }
    override fun bind(t: User) {
        binding.user = t
    }
}

//class UserViewHolder(parent: ViewGroup, viewModel: LobbyViewModel) :
//        DataBoundViewHolder<User, UserItemBinding>(parent, UserItemBinding::inflate) {
//    init {
//        //binding.showFullName = true
//        binding.root.setOnClickListener {
//
//       }
//    }
//    override fun bind(t: User) {
//        binding.user = t
//    }
//}



