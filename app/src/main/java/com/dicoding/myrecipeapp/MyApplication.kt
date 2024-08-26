package com.dicoding.myrecipeapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dicoding.myrecipeapp.core.utils.DarkMode
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setAppThemeMode()
    }

    private fun setAppThemeMode() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val darkModePreference = preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_follow_system)
        ) ?: getString(R.string.pref_dark_follow_system)

        val mode = DarkMode.valueOf(darkModePreference.uppercase(Locale.US))
        AppCompatDelegate.setDefaultNightMode(mode.value)
    }
}
