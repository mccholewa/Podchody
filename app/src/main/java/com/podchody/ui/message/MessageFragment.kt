package com.podchody.ui.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.NavigationController
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.ui.lobby.LobbyViewModel
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Misiu on 18.04.2018.
 */

class MessageFragment: Fragment() {


    @Inject
    lateinit var viewModelProvider: Provider<LobbyViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var db: FirebaseDatabase

    @Inject
    lateinit var auth: FirebaseAuth


    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  binding.viewModel = viewModel
        viewModel.liveData.observe(this){
            //     binding.state = it
        }
        viewModel.uiActions.observe(this){it(activity!!)}

    }
}