package com.podchody.ui.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.component
import com.podchody.util.viewModelProvider
import android.databinding.ViewDataBinding


class LoginFragment : Fragment() {


    lateinit var binding: LoginFragmentBinding

    private val viewModel by viewModelProvider { component.loginViewModel }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            binding.state = it
            binding.viewModel = viewModel
        viewModel.liveData.observe(this){
            binding.executePendingBindings()
        }
        viewModel.uiActions.observe(this){it(activity!!)}
    }
}
