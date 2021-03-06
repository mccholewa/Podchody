package com.podchody.ui.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.ViewModelFactory
import com.podchody.util.viewModelProvider
import kotlinx.android.synthetic.main.login_fragment.view.*
import javax.inject.Inject
import javax.inject.Provider


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.open_register.setOnClickListener{viewModel.openRegister()}

        viewModel.liveData.observe(this){
                binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}

        binding.viewModel = viewModel
    }
}

