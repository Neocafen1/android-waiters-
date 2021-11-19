package com.example.neocafewaiterapplication.view.utils.recycler_adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.databinding.ReceiptItemBinding
import com.example.neocafewaiterapplication.view.utils.Consts
import com.example.neocafewaiterapplication.view.utils.changeColor
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

class OrderRecyclerAdapter(val clickListener: RecyclerItemClick) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<AllModels.Order>()

    fun setList(list: MutableList<AllModels.Order>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ReceiptItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReceiptItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.clickListener(list[position])
        }

        val binding = holder.binding
        with(list[position]) {
            changeBackgroundColor(binding.statusCard, status, binding.statusText)
            binding.tableNumber.text = "Стол №$tableId"
            binding.orderNumber.text = "Заказ №$id"
            binding.time.text = creationDate
        }
    }

    private fun changeBackgroundColor(cardView: CardView, filter: String, textView: TextView) {
        when (filter) {
            Consts.READY -> {
                cardView.changeColor(Consts.READY_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
                textView.text = "Готово"
            }
            Consts.CANCELLED -> {
                cardView.changeColor(Consts.CANCEL_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
                textView.text = "Отменено"
            }
            Consts.IN_PROCESS -> {
                cardView.changeColor(Consts.IN_PROCESS_COLOR)
                textView.setTextColor(Color.parseColor(Consts.DARK))
                textView.text = "В процессе"
            }
            Consts.NEW -> {
                cardView.changeColor(Consts.NEW_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
                textView.text = "Новый"
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

}