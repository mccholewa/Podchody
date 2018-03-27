package com.podchody.vo

/**
 * Created by Misiu on 01.02.2018.
 */

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val Email: String,
    val Password: String
)