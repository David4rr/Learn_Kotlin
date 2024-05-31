package com.davidarrozaqi.storyapp

import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.davidarrozaqi.storyapp.base.BaseActivity
import com.davidarrozaqi.storyapp.databinding.ActivityMainBinding
import com.davidarrozaqi.storyapp.utils.ConstVal.bottomBar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun assignBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity() {
        super.initActivity()
        installSplashScreen()
    }

    override fun initSomething() {
        super.initSomething()
        setupBottomBar()
    }

    private fun setupBottomBar() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController: NavController = navHost.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (bottomBar.contains(destination.id)) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }
}