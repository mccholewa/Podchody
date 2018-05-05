package com.podchody.ui.lobby

import android.arch.lifecycle.ViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.podchody.NavigationController
import com.podchody.modle.GameFb
import com.podchody.modle.UserFb
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
        private val db: FirebaseDatabase,
        private val auth: FirebaseAuth,
        private val coroutines: Coroutines

) : ViewModel() {

    val liveData = LiveDataDelegate(LobbyViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

}


