package com.podchody.ui.login


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.viewModelProvider
import com.podchody.component

class LoginFragment : Fragment() {

    //private val viewModel by viewModelProvider { component.loginViewModel}

    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
