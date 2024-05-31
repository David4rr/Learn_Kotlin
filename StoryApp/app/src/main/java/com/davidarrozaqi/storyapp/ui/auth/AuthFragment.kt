package com.davidarrozaqi.storyapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.databinding.FragmentAuthBinding
import com.davidarrozaqi.storyapp.utils.PreferenceManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        lifecycleScope.launch {
            when (preferenceManager.getToken.isNotEmpty()) {
                true -> {
                    val direction =
                        AuthFragmentDirections.actionAuthFragmentToHomeFragment()
                    findNavController().navigate(direction)
                }

                else -> {
                    val direction =
                        AuthFragmentDirections.actionAuthFragmentToLoginFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}