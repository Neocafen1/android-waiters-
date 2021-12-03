package com.example.neocafewaiterapplication.view.utils.sealed_classes

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.*
import com.example.neocafewaiterapplication.view.utils.Consts
import com.example.neocafewaiterapplication.view.utils.changeColor

sealed class AllViewHolders(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class MenuViewHolder(val binding: MenuItemBinding) : AllViewHolders(binding) {
        fun bind(item: AllModels.Menu) {
            with(binding) {
                menuText.text = item.category
                menuIcon.setImageResource(item.icon)
            }

        }
    }

    class NotificationViewHolder(val binding: NotificationItemBinding) : AllViewHolders(binding) {

        fun bind(item: AllModels.Notification) {
            val color = when (item.title) {
                "Заказ готов" -> R.color.main_enable_color
                "Бариста принял заказ" -> R.color.main_enable_color
                else -> R.color.white
            }

            with(binding) {
                status.setTextColor(ContextCompat.getColor(status.context, color))
                status.text = item.title
                time.text = item.time
                receipt.text = item.description
            }
        }
    }

    class TableViewHolder(val binding: TableItemBinding) : AllViewHolders(binding) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllModels.Table) {
            binding.tableNumber.text = "Стол №${item.number}"

            if (item.user == null) setData(Consts.FREE_COLOR, "Свободен")
            else setData(Consts.RED, "Занят")
        }

        private fun setData(color: String, status: String) {
            binding.tableStatusColor.changeColor(color)
            binding.status.text = status
        }

    }

    class ProductInfoViewHolder(val binding:ProductInfoItemBinding) : AllViewHolders(binding){

        @SuppressLint("SetTextI18n")
        fun bind(item:AllModels.ProductOfReceipt){
            with(binding){
                productName.text = item.productTitle
                price.text = "${item.price}c за шт"
                totalPrice.text = "${item.sum}c"
                county.text = "x${item.quantity}"
            }
        }
    }

}
