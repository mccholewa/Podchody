package com.podchody.api


/**
 * Created by Misiu on 30.03.2018.
 */



interface AuthFb{
    fun signin(email: String?,password: String?): String?
}