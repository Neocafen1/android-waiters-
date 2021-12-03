package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.CustomAlertDialogItemBinding

class FinalReceiptAlertDialog(val title:String, val positive:() -> Unit, val negative:() -> Unit) : BaseAlertDialog<CustomAlertDialogItemBinding>() {

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
            negative()
            dismiss()
        }
        binding.close.setOnClickListener {
            negative()
            dismiss() }

        binding.positive.setOnClickListener {
            positive()
            dismiss()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, ): CustomAlertDialogItemBinding {
        return CustomAlertDialogItemBinding.inflate(inflater)
    }
}