package com.dicoding.myrecipeapp

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.myrecipeapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBind.root)

        supportActionBar?.hide()

        setupSplashScreen()
        setupBottomNavigation()
    }

    private fun setupSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val parent = splashScreenView.parent as? ViewGroup
                createSplashScreenExitAnimation(splashScreenView, parent).start()
            }
        }
    }

    private fun createSplashScreenExitAnimation(
        splashScreenView: View,
        parent: ViewGroup?
    ): ObjectAnimator {
        return ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            -splashScreenView.height.toFloat()
        ).apply {
            interpolator = AnticipateInterpolator()
            duration = 200L
            doOnEnd { parent?.removeView(splashScreenView) }
        }
    }

    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = mainBind.navView
        val navController = findNavController(R.id.nav_host_fragment_main_activity)
        navView.setupWithNavController(navController)
    }
}
