package com.fundito.fundito.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R

/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//        },2000L)
    }

}