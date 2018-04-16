package com.podchody.ui.lobby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.podchody.R
import com.podchody.databinding.LobbyFragmentBinding
import com.podchody.ui.common.DataBoundListAdapter
import com.podchody.util.ViewModelFactory
import com.podchody.vo.StringLanguageResource
import com.podchody.vo.User
import com.podchody.vo.orElse
import dagger.android.support.AndroidSupportInjection
import java.util.*
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

//        var options: FirebaseRecyclerOptions<User> = FirebaseRecyclerOptions.Builder<User>()
//                .setQuery()

//        val adapter = DataBoundListAdapter{UserViewHolder(it,viewModel)}
//        binding.userList.adapter = adapter
        viewModel.liveData.observe(this){
            binding.state = it
          //  adapter.replace(it.userList)
            //binding.executePendingBindings()
        }

        viewModel.uiActions.observe(this){it(activity!!)}

        binding.viewModel = viewModel


        viewModel.testData()
    }

}