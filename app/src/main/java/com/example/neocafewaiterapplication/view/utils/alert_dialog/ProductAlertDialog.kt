package com.example.neocafewaiterapplication.view.utils.alert_dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.view.bottom_navigation.new_order.new_order_products.NewOrderProducts
import com.example.neocafewaiterapplication.databinding.ProductAlertDialogBinding
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import org.koin.android.ext.android.bind


class ProductAlertDialog(private val model: AllModels.Product) : BaseAlertDialog<ProductAlertDialogBinding>() {
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            imageView2.setOnClickListener { dismiss() }
            name.text = model.title
            price.text = "${model.price} c"
            quantity.text = model.county.toString()
            showTotalPrice()

            plus.setOnClickListener {
                model.county += 1
                quantity.text = model.county.toString()
                showTotalPrice()
                (parentFragment as NewOrderProducts).update("+", model.id) // Обновляет кол во и в главном фрашменте
            }
            minus.setOnClickListener {
                if (model.county != 0){
                    model.county -= 1
                    quantity.text = model.county.toString()
                    showTotalPrice()
                    (parentFragment as NewOrderProducts).update("-", model.id)
                }
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showTotalPrice(){
        binding.total.text = "${model.price * model.county} c"
    }
}