package com.example.neocafewaiterapplication.view.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.google.android.material.snackbar.Snackbar

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun String.showToast(context: Context, duration:Int){
    Toast.makeText(context,this,  duration).show()
}

fun String.showSnackBar(view: View){
    Snackbar.make(view, this,Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor(Consts.RED))
        .show()
}

fun String.logging(){
    Log.i("TAG", this)
}

fun String.bearerToken():String{
    return "Bearer $this"
}

fun CardView.changeColor(color:String){
    this.setCardBackgroundColor(Color.parseColor(color))
}

fun MutableList<AllModels.Product>.getTotalPrice():Int{
    var totalPrice = 0
    this.forEach {
        if (it.county > 0){
            totalPrice += it.county * it.price
        }
    }
    return totalPrice
}

fun MutableList<AllModels.Product>.sortByCategory(category:String): MutableList<AllModels.Product> {
    val myList = mutableListOf<AllModels.Product>()
    if (category == "Все") {
        return this
    } else {
        this.forEach {
            if (it.category_name == category) {
                myList.add(it)
            }
        }
        return myList
    }
}