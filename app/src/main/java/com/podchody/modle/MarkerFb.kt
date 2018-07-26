package com.podchody.modle

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ServerValue



/**
 * Created by Misiu on 19.04.2018.
 */


@IgnoreExtraProperties
class MarkerFb{
    var message: String? = null
    var found: Boolean? = null
    var lat: Double? = null
    var lng: Double? = null
    var date: Long? = null
    var key: String? = null

    constructor(){}//needed for firebase to work

    constructor(message: String,found: Boolean, lat: Double, lng: Double,key: String){
        this.message = message
        this.found = found
        this.lat = lat
        this.lng = lng
        this.key = key
    }

    constructor(message: String,found: Boolean, lat: Double, lng: Double){
        this.message = message
        this.found = found
        this.lat = lat
        this.lng = lng
    }


//    @Exclude
//    fun toMap(): Map<String,Any>{
//        val result = HashMap<String, Any>()
//        result.put("message", message!!)
//        result.put("online", connected!!)
//        result.put("name", name!!)
//        result.put("online", connected!!)
//        result.put("name", name!!)
//        return result
//    }

}