package com.podchody.ui.newuser

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.podchody.databinding.NewUserFragmentBinding

/**
 * Created by Misiu on 10.01.2018.
 */

class NewUserFragment : Fragment() {

    lateinit var binding: NewUserFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        binding = NewUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}