package com.podchody.ui.register

/**
 * Created by Misiu on 10.01.2018.
 */

data class RegisterViewState(
        var email: String = "",
        var password: String = "",
        var loading: Boolean = false
)