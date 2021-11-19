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
            val color = when (item.status) {
                "Заказ готов" -> Consts.GREEN
                "Бариста принял заказ" -> Consts.GREEN
                else -> Consts.WHITE
            }

            with(binding) {
                status.setTextColor(ContextCompat.getColor(status.context, color))
                status.text = item.status
                time.text = item.time
                receipt.text = item.products
            }
        }
    }

    class TableViewHolder(val binding: TableItemBinding) : AllViewHolders(binding) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllModels.Table) {
            binding.tableNumber.text = "Стол №${item.id}"

            if (item.status) setData(Consts.FREE_COLOR, "Свободен")
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
                productName.text = item.name
                price.text = "${item.price}c за шт"
                totalPrice.text = "${item.price * item.county}"
                status.text = item.status
                county.text = item.county.toString()

                when(item.status){
                    "Готово" -> statusColor.changeColor(Consts.READY_COLOR)
                    "В процессе" -> statusColor.changeColor(Consts.IN_PROCESS_COLOR)
                    "Новый" -> statusColor.changeColor(Consts.NEW_COLOR)
                }
            }
        }
    }

    class WorkTimeViewHolder(val binding: WorkTimeItemBinding) : AllViewHolders(binding){

        fun bind(item:AllModels.WorkTime){
            with(binding){
                day.text = item.day
                morningTime.text = item.startTime
                eveningTime.text = item.endTime

                val color = when(item.work){
                    "Morning" -> R.color.afternoon_time_work
                    "Evening" -> R.color.night_work_time
                    else -> R.color.main_enable_color
                }

                day.setTextColor(ContextCompat.getColor(day.context, color))
            }
        }
    }


}
