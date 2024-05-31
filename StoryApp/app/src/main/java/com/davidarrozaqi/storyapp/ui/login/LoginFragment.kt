package com.davidarrozaqi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.auth.LoginRequest
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentLoginBinding
import com.davidarrozaqi.storyapp.utils.showToast
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
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
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val welcome =
            ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f)
                .setDuration(100)
        val email =
            ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f)
                .setDuration(100)
        val password =
            ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f)
                .setDuration(100)
        val button =
            ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f)
                .setDuration(100)
        val register =
            ObjectAnimator.ofFloat(binding.tvValidation, View.ALPHA, 1f)
                .setDuration(100)

        AnimatorSet().apply {
            playSequentially(welcome, email, password, button, register)
        }.start()
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {
                    binding.loadingButton.root.visibility = View.VISIBLE
                }

                is ApiResponse.Success -> {
                    showToast(requireActivity(), it.data.message)
                    val direction =
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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
        val signUpText = getString(R.string.text_validation)
        val spannableString = SpannableString(signUpText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val direction =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(direction)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // Add underline
                ds.isFakeBoldText = true // Add bold
                ds.color =
                    ContextCompat.getColor(requireContext(), R.color.pink_500) // Set text color
            }
        }

        val start = signUpText.indexOf("Sign Up")
        val end = start + "Sign Up".length

        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvValidation.text = spannableString
        binding.tvValidation.movementMethod = LinkMovementMethod.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text?.trim().toString()
            val password = binding.edtPassword.text?.trim().toString()

            val request = LoginRequest(
                email,
                password,
            )

            if (email.isEmpty()) {
                binding.edtEmail.error = getString(R.string.email_field_empty)
            } else if (password.isEmpty()) {
                binding.edtEmail.error = getString(R.string.password_field_empty)
            } else {
                viewModel.login(request)
            }
        }
    }
}