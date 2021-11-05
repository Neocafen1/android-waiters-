package com.example.neocafewaiterapplication.tools

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment <Binding: ViewBinding> : Fragment() {

    private var _binding:Binding? = null
    val binding get() = _binding!!
    val sharedPref by lazy { activity?.getPreferences(Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateView(inflater, container)
        setUpAppBar()
        return binding.root
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?):Binding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setUpAppBar()
}