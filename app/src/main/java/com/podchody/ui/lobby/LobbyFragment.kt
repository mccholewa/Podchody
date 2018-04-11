package com.podchody.ui.lobby

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.LobbyFragmentBinding
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Misiu on 03.04.2018.
 */

class LobbyFragment: Fragment() {

    @Inject
    lateinit var viewModelProvider: Provider<LobbyViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val RC_SIGN_IN: Int = 123


    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: LobbyFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = LobbyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(this){
            binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}

        binding.viewModel = viewModel
    }
}