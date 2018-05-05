package com.podchody.modle

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Misiu on 16.04.2018.
 */

@IgnoreExtraProperties
class UserFb{
    var email: String? = null
    var connected: Boolean? = null
    var name: String? = null

    constructor(){}//needed for firebase to work

    constructor(email: String,name: String, connected: Boolean){
        this.email = email
        this.connected = connected
        this.name = name
    }

    @Exclude
    fun toMap(): Map<String,Any>{
        val result = HashMap<String, Any>()
        result.put("email", email!!)
        result.put("online", connected!!)
        result.put("name", name!!)
        return result
    }

}