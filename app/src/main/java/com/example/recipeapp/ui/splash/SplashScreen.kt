package com.example.recipeapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Main).launch {
            navigateToHome()
        }
    }

    suspend fun navigateToHome() {
        delay(3000)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}