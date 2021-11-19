package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseAlertDialog<Binding : ViewBinding>() : DialogFragment() {
    val alertDialog: AlertDialog.Builder? = null

    private var _binding: Binding? = null
    val binding: Binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflateView(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?): Binding
}