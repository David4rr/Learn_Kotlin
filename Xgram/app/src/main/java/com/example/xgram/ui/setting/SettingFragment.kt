package com.example.xgram.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.xgram.data.factory.ViewModelPrefsFactory
import com.example.xgram.data.model.SettingViewModel
import com.example.xgram.data.prefs.SettingPreference
import com.example.xgram.data.prefs.dataStore
import com.example.xgram.databinding.FragmentSettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding
    private val themeList = listOf("Light", "Dark", "System")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.topBar?.apply {
            setNavigationOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            title = "Setting"
        }

        val settingViewmodel = obtainViewModel()

       settingViewmodel.getDarkMode().observe(viewLifecycleOwner){checked ->
           binding?.btnTheme?.setOnClickListener{
               showThemeDialog(checked, settingViewmodel)
           }
           binding?.btnTheme?.text = when (checked){
               0 -> "Light"
               1 -> "Dark"
               else -> "System"
           }
       }
    }

    private fun showThemeDialog(checked: Int, settingViewmodel: SettingViewModel) {
        val builder = MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Theme")
            .setSingleChoiceItems(
                themeList.toTypedArray(),
                checked
            ){dialog, which ->
                val selectedItem = when (which){
                    0 -> AppCompatDelegate.MODE_NIGHT_NO
                    1 -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                dialog.dismiss()
                settingViewmodel.saveDarkMode(which)
                AppCompatDelegate.setDefaultNightMode(selectedItem)
            }
            .create()
        builder.show()
    }

    private fun obtainViewModel(): SettingViewModel {
        val pref = SettingPreference.getInstance(requireActivity().dataStore)
        return ViewModelProvider(
            this, ViewModelPrefsFactory(pref)
        )[SettingViewModel::class.java]
    }

}