package com.example.neocafewaiterapplication.view.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.google.android.material.snackbar.Snackbar

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.notClickable() {
    this.isClickable = false
}

fun View.clickable() {
    this.isClickable = true
}

fun String.showSnackBar(view: View){
    Snackbar.make(view, this,Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor(Consts.RED))
        .show()
}

fun Fragment.navigate(directions: NavDirections) {
    val controller = findNavController()
    val currentDestination = (controller.currentDestination as? FragmentNavigator.Destination)?.className
        ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
    if (currentDestination == this.javaClass.name) {
        controller.navigate(directions)
    }
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

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
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
