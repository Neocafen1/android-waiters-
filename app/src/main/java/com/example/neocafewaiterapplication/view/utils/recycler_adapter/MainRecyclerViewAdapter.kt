package com.example.neocafewaiterapplication.view.utils.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.*
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllViewHolders


class MainRecyclerViewAdapter(private val click: RecyclerItemClick?) : RecyclerView.Adapter<AllViewHolders>() {

    private var list = listOf<AllModels>()

    fun setList(list: List<AllModels>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolders {
        val inflater =  LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.menu_item -> {
                AllViewHolders.MenuViewHolder(
                    MenuItemBinding.inflate(inflater, parent, false))
            }

            R.layout.notification_item -> {
                AllViewHolders.NotificationViewHolder(
                    NotificationItemBinding.inflate(inflater, parent, false))
            }

            R.layout.product_info_item -> {
                AllViewHolders.ProductInfoViewHolder(
                    ProductInfoItemBinding.inflate(inflater, parent, false))
            }

            R.layout.work_time_item -> {
                AllViewHolders.WorkTimeViewHolder(
                    WorkTimeItemBinding.inflate(inflater, parent, false))
            }

            R.layout.table_item -> {
                AllViewHolders.TableViewHolder(
                    TableItemBinding.inflate(inflater, parent, false))
            }

            else -> throw IllegalArgumentException("Invalid Type from adapter")
        }
    }

    override fun onBindViewHolder(holder: AllViewHolders, position: Int) {
        val model = list[position]
        return when(holder) {
            is AllViewHolders.MenuViewHolder -> {
                holder.itemView.setOnClickListener { click?.clickListener(model) }
                holder.bind(model as AllModels.Menu)
            }
            is AllViewHolders.NotificationViewHolder -> holder.bind(model as AllModels.Notification)
            is AllViewHolders.TableViewHolder -> {
                holder.itemView.setOnClickListener { click?.clickListener(model) }
                holder.bind(model as AllModels.Table)
            }
            is AllViewHolders.ProductInfoViewHolder -> holder.bind(model as AllModels.ProductOfReceipt)
            is AllViewHolders.WorkTimeViewHolder -> holder.bind(model as AllModels.WorkTime)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is AllModels.Menu -> R.layout.menu_item
            is AllModels.Notification -> R.layout.notification_item
            is AllModels.Table -> R.layout.table_item
            is AllModels.ProductOfReceipt -> R.layout.product_info_item
            is AllModels.WorkTime -> R.layout.work_time_item
            else -> super.getItemViewType(position)
        }
    }
}