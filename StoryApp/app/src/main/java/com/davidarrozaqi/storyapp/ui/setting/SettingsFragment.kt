package com.davidarrozaqi.storyapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.databinding.FragmentSettingsBinding
import com.davidarrozaqi.storyapp.utils.ext.showChooserDialog
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val viewModel: SettingViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
    }

    private fun initListener() {
        binding.actionLogout.setOnClickListener {
            showChooserDialog(
                title = getString(R.string.log_out),
                message = getString(R.string.you_are_going_to_be_logged_out_from_the_app),
                positiveButtonText = getString(R.string.yes),
                negativeButtonText = getString(R.string.no),
                onPositiveClick = {
                    logOut()
                }
            )
        }
        binding.changeLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun logOut() {
        viewModel.logOut().apply {
            if (this) {
                val direction =
                    SettingsFragmentDirections.actionSettingsFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
        }
    }

}