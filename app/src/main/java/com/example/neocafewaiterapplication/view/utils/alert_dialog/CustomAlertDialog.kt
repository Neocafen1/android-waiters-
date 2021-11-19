package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.CustomAlertDialogItemBinding

class CustomAlertDialog(val title:String, val function:() -> Unit) : BaseAlertDialog<CustomAlertDialogItemBinding>() {
    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): CustomAlertDialogItemBinding {
        return CustomAlertDialogItemBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.alert_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.alert_dialog_height)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title

        binding.negative.setOnClickListener {
            dismiss()
        }

        binding.positive.setOnClickListener {
            dismiss()
            function()
        }
    }
}