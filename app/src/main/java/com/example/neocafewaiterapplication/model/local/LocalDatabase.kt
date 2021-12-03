package com.example.neocafeteae1prototype.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.neocafewaiterapplication.view.utils.Consts

class LocalDatabase (context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("TestApp", Context.MODE_PRIVATE)

    private val editor = prefs.edit()

    fun saveAccessToken(token: String?) {
        editor.putString(Consts.ACCESS, token)
        editor.apply()
    }

    fun saveRefreshToken(token: String?) {
        editor.putString(Consts.REFRESH, token)
        editor.apply()
    }

    fun saveUserNumber(number: Int?) {
        if (number != null) {
            editor.putInt(Consts.PHONE_NUMBER, number)
            editor.apply()
        }
    }

    fun saveUserPassword(password:String?){
        if (password != null) {
            editor.putString(Consts.PASSWORD, password)
            editor.apply()
        }
    }

    fun saveIsRegister(status:Boolean?){
        if (status != null) {
            editor.putBoolean(Consts.STATUS, status)
            editor.apply()
        }
    }

    fun fetchAccessToken(): String? {
        return prefs.getString(Consts.ACCESS, null)
    }

    fun fetchRefreshToken(): String? {
        return prefs.getString(Consts.REFRESH, null)
    }

    fun fetchUserNumber(): Int {
        return prefs.getInt(Consts.PHONE_NUMBER, 0)
    }

    fun fetchUserPassword():String?{
        return prefs.getString(Consts.PASSWORD, null)
    }

    fun fetchRegistrationStatus():Boolean{
        return prefs.getBoolean(Consts.STATUS, false)
    }

    fun clearData() {
        prefs.edit().remove(Consts.ACCESS).remove(Consts.REFRESH).remove(Consts.PHONE_NUMBER).remove(Consts.PASSWORD).remove(Consts.STATUS).apply()
    }
}