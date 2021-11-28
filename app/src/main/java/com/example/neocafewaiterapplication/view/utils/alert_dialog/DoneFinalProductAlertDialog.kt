package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.DoneFinalProductItemBinding
import com.example.neocafewaiterapplication.view.utils.logging

class DoneFinalProductAlertDialog(private val dfunction:()->Unit) : BaseAlertDialog<DoneFinalProductItemBinding>() {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): DoneFinalProductItemBinding {
        return DoneFinalProductItemBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.done_final_product_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.done_final_product_dialog_height)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ok.setOnClickListener {
            dfunction() }
    }
}