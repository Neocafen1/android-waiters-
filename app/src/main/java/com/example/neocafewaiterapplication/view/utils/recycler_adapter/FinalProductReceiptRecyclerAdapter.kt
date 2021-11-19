package com.example.neocafewaiterapplication.view.utils.recycler_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.databinding.ProductCardWithQuantityItemBinding
import com.example.neocafewaiterapplication.view.utils.delegates.SecondRecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

class FinalProductReceiptRecyclerAdapter(private val clicker: SecondRecyclerItemClick) :
    RecyclerView.Adapter<FinalProductReceiptRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<AllModels.Product>()

    fun setList(list: MutableList<AllModels.Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductCardWithQuantityItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardWithQuantityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        with(list[position]) {
            binding.productName.text = title
            binding.quantity.text = county.toString()
            binding.totalPrice.text = (price * county).toString()
            binding.price.text = "$price"

            binding.plus.setOnClickListener {
                county++
                binding.totalPrice.text = (price * county).toString()
                binding.quantity.text = county.toString()
                clicker.clickListener("+", this)
            }

            binding.minus.setOnClickListener {
                if (county > 0) {
                    county--
                    binding.totalPrice.text = (price * county).toString()
                    binding.quantity.text = county.toString()
                    clicker.clickListener("-", this)
                    if (county == 0) {
                        list.removeAt(position)
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}