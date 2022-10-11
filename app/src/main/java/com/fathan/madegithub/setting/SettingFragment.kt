package com.fathan.madegithub.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.fathan.madegithub.databinding.FragmentSettingBinding



class SettingFragment : Fragment() {

    private var _settingBinding : FragmentSettingBinding? = null
    private val settingBinding get() = _settingBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _settingBinding = FragmentSettingBinding.inflate(layoutInflater)
        return settingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomNavigationView = view.findViewById(com.fathan.madegithub.R.id.bottomNavView) as BottomNavigationView
        settingBinding.switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                settingBinding.switchTheme.isChecked = true
                settingBinding.tvContentTheme.text = "Dark Mode"
//                bottomNavigationView.setBackgroundColor(resources.getColor(com.fathan.madegithub.R.color.black))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                settingBinding.switchTheme.isChecked = false
                settingBinding.tvContentTheme.text = "Light Mode"
//                bottomNavigationView.setBackgroundColor(resources.getColor(com.fathan.madegithub.R.color.white))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _settingBinding = null
    }
}