package com.podchody.ui.lobby

import com.podchody.vo.Lce
import com.podchody.vo.Resource
import com.podchody.vo.User
import com.podchody.vo.orElse

/**
 * Created by Misiu on 03.04.2018.
 */



data class UserViewState(
        val list: List<User>
)

data class LobbyViewState(
        val users: Lce<UserViewState> = Lce.Success(UserViewState(emptyList()))
){
    val userList:List<User> = users.map { it.list }.orElse(emptyList())
}