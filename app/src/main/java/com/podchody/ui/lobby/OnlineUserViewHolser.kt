package com.podchody.ui.lobby

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.podchody.R
import com.podchody.databinding.UserItemBinding
import com.podchody.modle.UserFb
import com.podchody.ui.common.DataBoundViewHolder
import com.podchody.vo.User
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by Misiu on 12.04.2018.
 */

class OnlineUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var name: TextView

init {
    name = itemView.name_online
}
//    fun bind(userFb: UserFb){
//        with(userFb){
//            itemView.email_online.text = email
//        }
//    }
}

//class UserViewHolder(parent: ViewGroup, viewModel: LobbyViewModel) :
//        DataBoundViewHolder<UserFb, UserItemBinding>(parent, UserItemBinding::inflate) {
//    init {
//        //binding.showFullName = true
//        binding.root.setOnClickListener {
//
//       }
//    }
//    override fun bind(t: UserFb) {
//        binding.user = t
//    }
//}



