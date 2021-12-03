package com.example.neocafewaiterapplication.view.utils.recycler_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.databinding.ProductCardWithQuantityItemBinding
import com.example.neocafewaiterapplication.view.utils.delegates.SecondRecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.setSafeOnClickListener

class FinalProductReceiptRecyclerAdapter(private val clicker: SecondRecyclerItemClick) :
    RecyclerView.Adapter<FinalProductReceiptRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<AllModels.Product>()

    fun setList(list: MutableList<AllModels.Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductCardWithQuantityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: AllModels.Product, position: Int) {
            with(model) {
                binding.productName.text = title
                binding.quantity.text = county.toString()
                binding.price.text = "($price за шт)"
                binding.totalPrice.text = "${price*county} c"

                binding.plus.setSafeOnClickListener {
                    county++
                    binding.totalPrice.text = "${price*county} c"
                    binding.quantity.text = county.toString()
                    clicker.clickListener("+", this)
                }

                binding.minus.setSafeOnClickListener {
                    if (county > 0) {
                        county--
                        binding.totalPrice.text = "${price*county} c"
                        binding.quantity.text = county.toString()
                        clicker.clickListener("-", this)
                        if (county == 0) {
                            list.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardWithQuantityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}