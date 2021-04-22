package com.william.pokedex_clone.module.splash.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.william.pokedex_clone.R
import com.william.pokedex_clone.databinding.ActivitySplashBinding
import com.william.pokedex_clone.module.main.activity.MainActivity

class SplashActivity : AppCompatActivity() {
    lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

//        fade in animation
        val animtopOut = AnimationUtils.loadAnimation(this, R.anim.anim_translate)
        splashBinding.logo.animation = animtopOut

        animtopOut.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
    }
}