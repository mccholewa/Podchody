package com.podchody.ui.login

/**
 * Created by Misiu on 09.01.2018.
 */

data class LoginViewState(
        var email: String = "",
        var password: String = "",
        val loading: Boolean = false
)