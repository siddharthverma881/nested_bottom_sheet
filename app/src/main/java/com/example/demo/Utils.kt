package com.example.demo

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

object Utils {

    fun dpToPx(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpValue,
            context.resources.displayMetrics
        ).toInt()
    }

    fun setVisibility(visibility : Int, vararg views: View){
        for(view : View in views){
            view.visibility = visibility
        }
    }
}