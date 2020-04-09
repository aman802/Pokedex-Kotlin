package com.aman802.pokedb

import android.content.Context
import java.lang.StringBuilder

object SharedPref {

    @JvmStatic
    fun isFirstLogin(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Constants.IS_FIRST_LOGIN, true)
    }

    @JvmStatic
    fun setFirstLogin(context: Context, isFirstLogin: Boolean) {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.IS_FIRST_LOGIN, isFirstLogin)
        editor.apply()
    }

    @JvmStatic
    fun getFavoritesList(context: Context): ArrayList<Int> {
        val arrayList = ArrayList<Int>()
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val favoriteIDString = sharedPreferences.getString(Constants.FAVORITES_LIST, "")
        if (!favoriteIDString.isNullOrEmpty()) {
            val favoriteIDList = favoriteIDString.split(",")
            for (id in favoriteIDList) {
                arrayList.add(id.toInt())
            }
        }
        return arrayList
    }

    @JvmStatic
    fun addFavorite(context: Context, id: Int) {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        var favoriteIDString = sharedPreferences.getString(Constants.FAVORITES_LIST, "")
        if (favoriteIDString.isNullOrEmpty()) {
            favoriteIDString = "$id"
        }
        else {
            favoriteIDString += ",$id"
        }

        val editor = sharedPreferences.edit()
        editor.putString(Constants.FAVORITES_LIST, favoriteIDString)
        editor.apply()
    }

    @JvmStatic
    fun removeFavorite(context: Context, id: Int) {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoriteIDString = sharedPreferences.getString(Constants.FAVORITES_LIST, "")
        if (!favoriteIDString.isNullOrEmpty()) {
            val favoriteIDList = favoriteIDString.split(",")
            val stringBuilder = StringBuilder()
            for (currentID in favoriteIDList) {
                if (currentID.toInt() != id) {
                    stringBuilder.append(currentID).append(",")
                }
            }

            if (stringBuilder.isNotEmpty()) {
                stringBuilder.replace(stringBuilder.length - 1, stringBuilder.length, "");
            }
            editor.putString(Constants.FAVORITES_LIST, stringBuilder.toString())
            editor.apply()
        }
    }
}