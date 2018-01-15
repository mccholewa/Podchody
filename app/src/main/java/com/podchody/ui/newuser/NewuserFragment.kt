package com.podchody.ui.newuser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.component
import com.podchody.databinding.NewuserFragmentBinding
import com.podchody.util.viewModelProvider


/**
 * Created by Misiu on 10.01.2018.
 */

class NewuserFragment : Fragment() {

    private val viewModel by viewModelProvider { component.newuserViewModel }

    lateinit var binding: NewuserFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = NewuserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}