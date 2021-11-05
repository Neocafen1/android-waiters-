package com.example.neocafewaiterapplication.tools.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.ProductItemBinding
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels


class AllProductsRecyclerAdapter(private val click: RecyclerItemClick?) :
    RecyclerView.Adapter<AllProductsRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<AllModels.Product>()
    private var filter = ""

    fun setList(list: MutableList<AllModels.Product>, filter: String) {
        this.list = list
        this.filter = filter
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        with(list[position]) {
            if (category_name == filter) {

                if (county > 0) {
                    binding.constraintLayout.background = ContextCompat.getDrawable(
                        binding.constraintLayout.context,
                        R.color.main_enable_color
                    )
                } else binding.constraintLayout.background = ContextCompat.getDrawable(
                    binding.constraintLayout.context,
                    R.color.card_view_background
                )

                binding.name.text = title
                binding.price.text = price.toString()

                holder.itemView.setOnClickListener {
                    click?.clickListener(list[position])
                }
            }

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

}