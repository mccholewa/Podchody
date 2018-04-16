package com.podchody.ui.lobby

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.podchody.NavigationController
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import com.podchody.vo.Lce
import com.podchody.vo.Resource
import com.podchody.vo.User
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Misiu on 03.04.2018.
 */

class LobbyViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val database: FirebaseDatabase,
        private val auth: FirebaseAuth,
        private val coroutines: Coroutines

) : ViewModel(){

    val liveData = LiveDataDelegate(LobbyViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

   // var onlineRef = database.getReference().child(".info/connected")
  // var counterRef = database.getReference("LastOnline")
   // var currenttUserRef= database.getReference("lastOnline").child(auth.currentUser?.uid)


    fun testData() {
            Lce.exec({ state = state.copy(users = it) }) {
                val items = listOf(
                    User("misiu2314@gmai.com", "Online"),
                    User("misiu2314@gmai.com", "Online"),
                    User("misiu2314@gmai.com", "Online") )
                UserViewState(items)
            }
        }
    }

//    fun setupListeners(){
//        val eventListener = object:ValueEventListener{
//            override fun onDatachange(DataSnapshot dataSnapshot){
//
//            }
//        }
//        onlineRef.addValueEventListener()
//
//
//    }

