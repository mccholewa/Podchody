package com.podchody.ui.register

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.viewModelProvider
import com.podchody.databinding.RegisterFragmentBinding
import com.podchody.ui.login.LoginViewModel
import com.podchody.util.ViewModelFactory
import kotlinx.android.synthetic.main.register_fragment.view.*
import javax.inject.Inject
import javax.inject.Provider


/**
 * Created by Misiu on 10.01.2018.
 */

class RegisterFragment : Fragment() {

    @Inject lateinit var viewModelProvider: Provider<RegisterViewModel>

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }


    lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.register.setOnClickListener{ viewModel.registerUser() }
        viewModel.liveData.observe(this){
            binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}
        binding.viewModel = viewModel
    }
}