package com.aman802.pokedb.customViews

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.aman802.pokedb.R

class tagView (view: View) {

    private var linearLayout: LinearLayout = view as LinearLayout
    private var textView: TextView = view.findViewById(R.id.item_tag_name)

    fun setText(text: String) {
        textView.text = text
    }

    fun setVisibility(isVisible: Boolean) {
        if (isVisible) {
            linearLayout.visibility = View.VISIBLE
        }
        else {
            linearLayout.visibility = View.GONE
        }
    }

    fun setColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout.background.setTint(color)
        }
        textView.setTextColor(Color.parseColor("#FFFFFF"))
    }

}