package com.podchody.ui.login


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.podchody.R
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.KeyboardUtil
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.login_fragment.view.*
import javax.inject.Inject
import javax.inject.Provider
import java.util.Arrays.asList
import java.util.Arrays.asList






class LoginFragment : Fragment() {

    @Inject lateinit var viewModelProvider: Provider<LoginViewModel>

    @Inject lateinit var viewModelFactory: ViewModelFactory



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


        binding.root.login.setOnClickListener{
            viewModel.signin()
           // KeyboardUtil().hideKeyboardFrom(it.context, it)
        }
        binding.root.open_register.setOnClickListener{viewModel.openRegister()}

        binding.viewModel = viewModel

        viewModel.liveData.observe(this){
            binding.state = it
            binding.executePendingBindings()
        }

        viewModel.uiActions.observe(this){it(activity!!)}
    }
}

