package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.DoneItemBinding

class DoneCustomAlertDialog(private val title:String) : BaseAlertDialog<DoneItemBinding>() {

    private lateinit var avd:AnimatedVectorDrawableCompat
    private lateinit var avd2:AnimatedVectorDrawable

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DoneItemBinding {
        return DoneItemBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.alert_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.done_alert_dialog_height)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawable = binding.done.drawable
        binding.title.text = title
        if (drawable is AnimatedVectorDrawableCompat){
            avd = drawable as AnimatedVectorDrawableCompat
            avd.start()
        }else if(drawable is AnimatedVectorDrawable){
            avd2 = drawable as AnimatedVectorDrawable
            avd2.start()
        }
    }
}