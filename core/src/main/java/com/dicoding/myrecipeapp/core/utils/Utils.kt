package com.dicoding.myrecipeapp.core.utils

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.dicoding.myrecipeapp.core.R
import org.jsoup.Jsoup

fun setTextViewColor(
    context: Context,
    textView: TextView,
    condition: Boolean
) {
    val colorText = if (condition) {
        ContextCompat.getColor(context, R.color.blue_700)
    } else {
        ContextCompat.getColor(context, R.color.grey)
    }
    textView.setTextColor(colorText)
}

fun setImageViewTint(
    context: Context,
    imageView: ImageView,
    condition: Boolean
) {
    val colorIcon = if (condition) {
        ContextCompat.getColor(context, R.color.blue_700)
    } else {
        ContextCompat.getColor(context, R.color.grey)
    }
    val colorStateList = ColorStateList.valueOf(colorIcon)
    imageView.imageTintList = colorStateList
}

fun htmlParser(description: String): String {
    return Jsoup.parse(description).text()
}

enum class DarkMode(val value: Int) {
    FOLLOW_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO)
}