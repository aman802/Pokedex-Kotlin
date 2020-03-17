package com.aman802.pokedb

import android.content.Context
import androidx.core.content.ContextCompat
import java.lang.StringBuilder

object Helper {
    @JvmStatic
    fun getCamelCaseName(name: String): String {
        val stringBuilder = StringBuilder()
        for (i in name.indices) {
            if (i == 0) {
                stringBuilder.append(name[i].toUpperCase())
            }
            else {
                stringBuilder.append(name[i])
            }
        }

        return stringBuilder.toString()
    }

    @JvmStatic
    fun getThreeDigitNumber(number: String): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("(#")
        if (number.length == 1) {
            stringBuilder.append("00")
        }
        else if (number.length == 2) {
            stringBuilder.append("0")
        }
        stringBuilder.append(number)
        stringBuilder.append(")")

        return stringBuilder.toString()
    }

    @JvmStatic
    fun getColorForType(context: Context, type: String): Int {
        when (type.toLowerCase()) {
            "grass" -> return ContextCompat.getColor(context, R.color.grassType)
            "poison" -> return ContextCompat.getColor(context, R.color.poisonType)
            "bug" -> return ContextCompat.getColor(context, R.color.bugType)
            "dark" -> return ContextCompat.getColor(context, R.color.darkType)
            "dragon" -> return ContextCompat.getColor(context, R.color.dragonType)
            "ice" -> return ContextCompat.getColor(context, R.color.iceType)
            "electric"-> return ContextCompat.getColor(context, R.color.electricType)
            "normal" -> return ContextCompat.getColor(context, R.color.normalType)
            "fairy" -> return ContextCompat.getColor(context, R.color.fairyType)
            "fighting" -> return ContextCompat.getColor(context, R.color.fightingType)
            "psychic" -> return ContextCompat.getColor(context, R.color.psychicType)
            "fire" -> return ContextCompat.getColor(context, R.color.fireType)
            "rock" -> return ContextCompat.getColor(context, R.color.rockType)
            "flying" -> return ContextCompat.getColor(context, R.color.flyingType)
            "steel" -> return ContextCompat.getColor(context, R.color.steelType)
            "ghost" -> return ContextCompat.getColor(context, R.color.ghostType)
            "water" -> return ContextCompat.getColor(context, R.color.waterType)
            "ground" -> return ContextCompat.getColor(context, R.color.groundType)
            else -> return ContextCompat.getColor(context, R.color.tagBgColor)
        }
    }
}