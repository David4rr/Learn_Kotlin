package com.davidarrozaqi.storyapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    abstract fun assignBinding() : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivity()
        super.onCreate(savedInstanceState)
        binding = assignBinding()
        setContentView(binding.root)

        initSomething()
    }

    open fun initSomething(){}

    open fun initActivity(){}
}