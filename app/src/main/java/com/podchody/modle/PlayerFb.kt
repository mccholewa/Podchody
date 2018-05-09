package com.podchody.modle

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Misiu on 21.04.2018.
 */
@IgnoreExtraProperties
class PlayerFb{
    var uid:String?=null
    var role:String?=null
    var name:String?=null

    constructor(){}//needed for firebase to work

    constructor(uid: String, role: String,name:String) {
        this.uid = uid
        this.role = role
        this.name = name
    }
}