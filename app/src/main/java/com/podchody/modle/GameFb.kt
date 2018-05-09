package com.podchody.modle

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Misiu on 19.04.2018.
 */

@IgnoreExtraProperties
class GameFb{
    var title: String? = null
    var started:Boolean? = null
    var host: PlayerFb? = null
    var player: PlayerFb? = null

    constructor(){}//needed for firebase to work

    constructor(title: String, host: PlayerFb , player: PlayerFb?, started:Boolean, pointsFb: String?){
        this.started = started
        this.host = host
        this.title = title
        this.player = player
    }

//    constructor(name: String,ended: Boolean, hostid: String, playerid: String, pointFb: MarkerFb){
//        this.ended = ended
//        this.hostid = hostid
//        this.name = name
//        this.playerid = playerid
//        this.point = point
//    }

//    @Exclude
//    fun toMap(): Map<String,Any>{
//        val result = HashMap<String, Any>()
//        result.put("email", email!!)
//        result.put("online", connected!!)
//        result.put("name", name!!)
//        return result
//    }
}

