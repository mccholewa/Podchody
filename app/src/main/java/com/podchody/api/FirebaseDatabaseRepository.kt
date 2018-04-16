package com.podchody.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.swing.UIManager.put




/**
 * Created by Misiu on 30.03.2018.
 */



class FirebaseDatabaseRepository(private val db: FirebaseFirestore,private val auth: FirebaseAuth) {

    fun test(){}


    fun addUserToOnlineList(){


        db.collection("online")
                .document(auth.currentUser!!.uid)
                .set(data)
        }

    }

//    fun UserListEventListener()
//    {
//        override
//    }


}