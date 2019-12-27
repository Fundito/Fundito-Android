package com.fundito.fundito.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.coroutines.delay

/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            delay(1000L)
            startActivity(MainActivity::class)
        }
    }
}
