package com.davidarrozaqi.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterRequest
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentRegisterBinding
import com.davidarrozaqi.storyapp.utils.showToast
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
        initObserver()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(
            binding.ivLogo, View.TRANSLATION_X, -30f, 30f
        ).apply {
            duration = 5000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()

        val welcome =
            ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f)
                .setDuration(100)
        val name =
            ObjectAnimator.ofFloat(binding.edtUsername, View.ALPHA, 1f)
                .setDuration(100)
        val email =
            ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f)
                .setDuration(100)
        val password =
            ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f)
                .setDuration(100)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f)
                .setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                welcome,
                name,
                email,
                password,
                btnRegister
            )
        }.start()
    }

    private fun initObserver() {
        viewModel.registerResult.observe(this) {
            when (it) {
                is ApiResponse.Loading -> {
                    binding.loadingButton.root.visibility = View.VISIBLE
                }

                is ApiResponse.Success -> {
                    showToast(requireActivity(), it.data.message)
                    val direction =
                        RegisterFragmentDirections.actionRegisterFragmentToAuthFragment()
                    findNavController().navigate(direction)
                }

                is ApiResponse.Error -> {
                    showToast(requireActivity(), it.errorMessage)
                    binding.loadingButton.root.visibility = View.GONE
                }

                else -> {
                    binding.loadingButton.root.visibility = View.GONE
                }
            }
        }
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text?.trim().toString()
            val password = binding.edtPassword.text?.trim().toString()

            val request = RegisterRequest(
                name,
                email,
                password,
            )

            if (email.isEmpty()) {
                binding.edtEmail.error = getString(R.string.email_field_empty)
            } else if (password.isEmpty()) {
                binding.edtPassword.error = getString(R.string.password_field_empty)
            } else {
                viewModel.register(request)
            }
        }
    }

}