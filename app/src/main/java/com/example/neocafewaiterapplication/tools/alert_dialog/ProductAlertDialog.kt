package com.example.neocafewaiterapplication.tools.alert_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.bottom_navigation.new_order.new_order_products.NewOrderProducts
import com.example.neocafewaiterapplication.databinding.ProductAlertDialogBinding
import com.example.neocafewaiterapplication.root.MainActivity
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels


class ProductAlertDialog(private val model: AllModels) : BaseAlertDialog<ProductAlertDialogBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProductAlertDialogBinding {
        return ProductAlertDialogBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.product_alert_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.product_alert_dialog_height)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = model as AllModels.Product
        with(binding){
            imageView2.setOnClickListener { dismiss() }
            name.text = model.title
            price.text = model.price.toString()
            quantity.text = model.county.toString()
            plus.setOnClickListener {
                model.county += 1
                quantity.text = model.county.toString()
                (parentFragment as NewOrderProducts).update("+", model.title)
            }
            minus.setOnClickListener {
                if (model.county != 0){
                    model.county -= 1
                    quantity.text = model.county.toString()
                    (parentFragment as NewOrderProducts).update("-", model.title)
                }
            }


        }
    }
}