package com.podchody.ui.lobby

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.game_item.view.*

/**
 * Created by Misiu on 19.04.2018.
 */

class LobbyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var name: TextView

    init {
        name = itemView.game_name
    }
}