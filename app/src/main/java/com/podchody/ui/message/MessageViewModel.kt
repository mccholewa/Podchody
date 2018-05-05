package com.podchody.ui.message

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.NavigationController
import com.podchody.ui.map.MapViewState
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import javax.inject.Inject

/**
 * Created by Misiu on 19.04.2018.
 */


class MessageViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val database: FirebaseDatabase,
        private val auth: FirebaseAuth,
        private val coroutines: Coroutines

) : ViewModel() {

    val liveData = LiveDataDelegate(MapViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

}
