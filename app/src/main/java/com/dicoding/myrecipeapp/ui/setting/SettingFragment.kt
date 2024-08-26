package com.dicoding.myrecipeapp.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.myrecipeapp.R
import com.dicoding.myrecipeapp.core.utils.DarkMode
import java.util.Locale

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<ListPreference>(getString(R.string.pref_key_dark))?.apply {
            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    val selectedMode = mapToDarkMode(newValue as String)
                    updateTheme(selectedMode.value)
                    true
                }
        }
    }

    private fun mapToDarkMode(value: String): DarkMode {
        return when (value.uppercase(Locale.US)) {
            DarkMode.ON.name -> DarkMode.ON
            DarkMode.OFF.name -> DarkMode.OFF
            else -> DarkMode.FOLLOW_SYSTEM
        }
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }
}