package com.aman802.pokedb

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

object Helper {

    //Set up touch listener for non-text box views to hide keyboard.
    fun setupKeyboardHidingUI(view: View, activity: Activity) {
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(activity)
                false
            }
        }
        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupKeyboardHidingUI(innerView, activity)
            }
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
        }
    }

    @JvmStatic
    fun convertToDP(context: Context, sizeInDP: Int): Int {
        val scale = context.resources.displayMetrics.density
        return ((sizeInDP*scale + 0.5f).toInt());
    }

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

    @JvmStatic
    fun getBackgroundColorForType(context: Context, type: String): Int {
        when (type.toLowerCase()) {
            "grass" -> return ContextCompat.getColor(context, R.color.grassTypeTransparent)
            "poison" -> return ContextCompat.getColor(context, R.color.poisonTypeTransparent)
            "bug" -> return ContextCompat.getColor(context, R.color.bugTypeTransparent)
            "dark" -> return ContextCompat.getColor(context, R.color.darkTypeTransparent)
            "dragon" -> return ContextCompat.getColor(context, R.color.dragonTypeTransparent)
            "ice" -> return ContextCompat.getColor(context, R.color.iceTypeTransparent)
            "electric"-> return ContextCompat.getColor(context, R.color.electricTypeTransparent)
            "normal" -> return ContextCompat.getColor(context, R.color.normalTypeTransparent)
            "fairy" -> return ContextCompat.getColor(context, R.color.fairyTypeTransparent)
            "fighting" -> return ContextCompat.getColor(context, R.color.fightingTypeTransparent)
            "psychic" -> return ContextCompat.getColor(context, R.color.psychicTypeTransparent)
            "fire" -> return ContextCompat.getColor(context, R.color.fireTypeTransparent)
            "rock" -> return ContextCompat.getColor(context, R.color.rockTypeTransparent)
            "flying" -> return ContextCompat.getColor(context, R.color.flyingTypeTransparent)
            "steel" -> return ContextCompat.getColor(context, R.color.steelTypeTransparent)
            "ghost" -> return ContextCompat.getColor(context, R.color.ghostTypeTransparent)
            "water" -> return ContextCompat.getColor(context, R.color.waterTypeTransparent)
            "ground" -> return ContextCompat.getColor(context, R.color.groundTypeTransparent)
            else -> return ContextCompat.getColor(context, R.color.tagBgColorTransparent)
        }
    }

    @JvmStatic
    fun getPercentForStat(statValue: Int, statName: String): Int {
        val maxValue = getMaxValueForStat(statName)
        if (maxValue == -1)
            return -1

        val division= (statValue.toFloat() / maxValue)
        return (division * 100).roundToInt()
    }

    @JvmStatic
    fun getMaxValueForStat(statName: String): Int {
        return when {
            "hp".equals(statName, true) -> 250
            "attack".equals(statName, true) -> 134
            "defense".equals(statName, true) -> 180
            "speed".equals(statName, true) -> 140
            else -> -1
        }
    }

    @JvmStatic
    fun getMinValueForStat(statName: String): Int {
        return when {
            "hp".equals(statName, true) -> 10
            "attack".equals(statName, true) -> 5
            "defense".equals(statName, true) -> 5
            "speed".equals(statName, true) -> 15
            else -> -1
        }
    }

    @JvmStatic
    fun getWeaknessesForType(type1: String, type2: String?): ArrayList<String> {
        val weaknessList = ArrayList<String>()
        if (type2 == null) {
            when (type1.toLowerCase()) {
                "normal" -> {
                    weaknessList.add("fighting")
                }
                "fire" -> {
                    weaknessList.add("water")
                    weaknessList.add("ground")
                    weaknessList.add("rock")
                }
                "water" -> {
                    weaknessList.add("electric")
                    weaknessList.add("grass")
                }
                "electric" -> {
                    weaknessList.add("ground")
                }
                "grass" -> {
                    weaknessList.add("fire")
                    weaknessList.add("ice")
                    weaknessList.add("poison")
                    weaknessList.add("flying")
                }
                "ice" -> {
                    weaknessList.add("fire")
                    weaknessList.add("fighting")
                    weaknessList.add("rock")
                    weaknessList.add("steel")
                }
                "fighting" -> {
                    weaknessList.add("flying")
                    weaknessList.add("psychic")
                    weaknessList.add("fairy")
                }
                "poison" -> {
                    weaknessList.add("ground")
                    weaknessList.add("psychic")
                }
                "ground" -> {
                    weaknessList.add("water")
                    weaknessList.add("grass")
                    weaknessList.add("ice")
                }
                "flying" -> {
                    weaknessList.add("electric")
                    weaknessList.add("ice")
                    weaknessList.add("rock")
                }
                "psychic" -> {
                    weaknessList.add("bug")
                    weaknessList.add("ghost")
                    weaknessList.add("dark")
                }
                "bug" -> {
                    weaknessList.add("fire")
                    weaknessList.add("flying")
                    weaknessList.add("rock")
                }
                "rock" -> {
                    weaknessList.add("water")
                    weaknessList.add("grass")
                    weaknessList.add("fighting")
                    weaknessList.add("ground")
                    weaknessList.add("steel")
                }
                "ghost" -> {
                    weaknessList.add("ghost")
                    weaknessList.add("dark")
                }
                "dragon" -> {
                    weaknessList.add("ice")
                    weaknessList.add("fairy")
                    weaknessList.add("dragon")
                }
                "dark" -> {
                    weaknessList.add("fighting")
                    weaknessList.add("bug")
                    weaknessList.add("fairy")
                }
                "steel" -> {
                    weaknessList.add("fire")
                    weaknessList.add("fighting")
                    weaknessList.add("ground")
                }
                "fairy" -> {
                    weaknessList.add("poison")
                    weaknessList.add("steel")
                }
            }

//            return weaknessList
        }
//        else {
//            return getWeaknessesForDualType(type1, type2)
//        }
        return weaknessList
    }

//    private fun getWeaknessesForDualType(type1: String, type2: String): ArrayList<String> {
//
//    }
}