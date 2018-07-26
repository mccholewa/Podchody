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

}



