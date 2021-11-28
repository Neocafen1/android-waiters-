package com.example.neocafewaiterapplication.view.utils.recycler_adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.databinding.WorkTimeItemBinding
import com.example.neocafewaiterapplication.view.utils.Consts
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

class ScheduleRecyclerAdapter : RecyclerView.Adapter<ScheduleRecyclerAdapter.ViewHolder>() {

    private var list = listOf<AllModels.WorkTime>()

    fun setList(list:List<AllModels.WorkTime>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding:WorkTimeItemBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(model:AllModels.WorkTime){
            val russianDay = translateToRussian(model.day)
                with(binding){
               when(model.work_time){
                   "DW" ->{
                       day.text = russianDay
                       time.text = "C 09:00 до 16:00"
                       day.setTextColor(Color.parseColor(Consts.DAY_WORK_COLOR))
                       time.setTextColor(Color.parseColor(Consts.DAY_WORK_COLOR))
                   }

                   "NW" -> {
                       day.text = russianDay
                       time.text = "C 16:00 до 22:00"
                       day.setTextColor(Color.parseColor(Consts.NIGHT_WORK_COLOR))
                       time.setTextColor(Color.parseColor(Consts.NIGHT_WORK_COLOR))
                   }

                   else -> {
                       day.setTextColor(Color.parseColor(Consts.READY_COLOR))
                       time.setTextColor(Color.parseColor(Consts.READY_COLOR))
                       day.text = russianDay
                       time.text = "Выходной"
                   }
               }
           }
        }
    }


    fun translateToRussian(day:String):String{
        return when(day){
            "monday" -> "Понедельник"
            "tuesday" -> "Вторник"
            "wednesday" -> "Среда"
            "thursday" -> "Четверг"
            "friday" -> "Пятница"
            "saturday" -> "Суббота"
            else -> "Воскресенье"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorkTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}