package com.example.stock.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.stock.BuildConfig
import com.example.stock.R
import com.example.stock.databinding.SettingsViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: SettingsViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appVersion = BuildConfig.APP_VERSION
        val sharedPreferences = requireContext().getSharedPreferences(
            SettingsFragment::class.simpleName,
            Context.MODE_PRIVATE
        )
        val isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false)
        binding.themeButton.isChecked = isDarkMode
        binding.themeButton.setOnClickListener {
            val toggledDarkMode = !isDarkMode
            sharedPreferences.edit(commit = true) {
                putBoolean(DARK_MODE_KEY, toggledDarkMode)
            }

            AppCompatDelegate.setDefaultNightMode(
                if (toggledDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }

    companion object {
        private const val DARK_MODE_KEY: String = "IS_DARK_MODE"
    }
}