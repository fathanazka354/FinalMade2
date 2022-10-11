package com.fathan.madegithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fathan.madegithub.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _splashBinding : ActivitySplashBinding? = null
    private val splashBinding get() = _splashBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val handler = Handler(mainLooper)

        handler.postDelayed({
            Intent(this, MainActivity::class.java).let {
                startActivity(it)
                finish()
            }
        }, TIME_SPLASH)


    }

    override fun onDestroy() {
        super.onDestroy()
        _splashBinding = null
    }
    companion object{
        private const val TIME_SPLASH = 4000L
    }
}