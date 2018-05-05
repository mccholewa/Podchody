package com.podchody.api

import com.podchody.modle.UserFb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.podchody.modle.GameFb
import timber.log.Timber


/**
 * Created by Misiu on 30.03.2018.
 */



class FirebaseDatabaseRepository(private val db: FirebaseDatabase,private val auth: FirebaseAuth) {

    fun writeUser(){
        if(auth.currentUser != null) {
//            val user = UserFb(auth.currentUser!!.email!!, auth.currentUser!!.displayName!!, true)
            db.reference.child("users").child(auth.currentUser!!.uid).child("connected").setValue(true)
            db.reference.child("users").child(auth.currentUser!!.uid).child("email").setValue(auth.currentUser!!.email!!)
            db.reference.child("users").child(auth.currentUser!!.uid).child("name").setValue(auth.currentUser!!.displayName!!)

//            db.reference.child("users").child(auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
//                Timber.d("User added successfully")
//            }.addOnFailureListener {
//                Timber.d("User wasn't added")
//            }
        }
    }

    fun setUserOffline(){
        db.reference.child("users").child(auth.currentUser!!.uid).child("connected").setValue(false).addOnSuccessListener {
            Timber.d("User was set offline")
        }

    }


    fun getOnlineUsers(): Query{
        return db.reference.child("users").orderByChild("connected").equalTo(true)
    }


}