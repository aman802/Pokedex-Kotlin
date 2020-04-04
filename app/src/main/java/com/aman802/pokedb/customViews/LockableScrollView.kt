package com.aman802.pokedb.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class LockableScrollView : ScrollView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    private var isScrollable = false

    fun setScrollable(isScrollable: Boolean) {
        this.isScrollable = isScrollable
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (isScrollable) {
            super.onTouchEvent(ev)
            super.performClick()
            return true
        }
        else {
            return false
        }
//        return isScrollable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollable && super.onInterceptTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        return isScrollable && super.performClick()
    }

}