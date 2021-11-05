package com.example.neocafewaiterapplication.tools.recycler_adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.databinding.ProductItemBinding
import com.example.neocafewaiterapplication.databinding.ReceiptItemBinding
import com.example.neocafewaiterapplication.tools.Consts
import com.example.neocafewaiterapplication.tools.changeColor
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

class OrderRecyclerAdapter(val clickListener:RecyclerItemClick) : RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<AllModels.Order>()
    private var filter = ""

    fun setList(list:MutableList<AllModels.Order>, filter:String){
        this.list = list
        this.filter = filter
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding:ReceiptItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReceiptItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.clickListener(list[position])
        }

        val binding = holder.binding
        with(list[position]){
            if (status == filter || filter == "Все"){
                changeBackgroundColor(binding.statusCard, status, binding.statusText)
                binding.tableNumber.text = "Стол №$tabelNumber"
                binding.orderNumber.text = "Заказ №$orderNumber"
                binding.statusText.text = status
                binding.time.text = time
            }
        }
    }

    private fun changeBackgroundColor(cardView: CardView, filter:String, textView: TextView){
        when(filter){
            "Готово" -> {
                cardView.changeColor(Consts.READY_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
            }
            "Отменено" -> {
                cardView.changeColor(Consts.CANCEL_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
            }
            "В процессе" -> {
                cardView.changeColor(Consts.IN_PROCESS_COLOR)
                textView.setTextColor(Color.parseColor(Consts.DARK))
            }
            "Новый" -> {
                cardView.changeColor(Consts.NEW_COLOR)
                textView.setTextColor(Color.parseColor(Consts.WHITE_FOR_PARSE))
            }
            else -> {
                cardView.changeColor(Consts.END_COLOR)
                textView.setTextColor(Color.parseColor(Consts.DARK))
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

}