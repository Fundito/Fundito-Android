package com.fundito.fundito.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import kotlinx.android.synthetic.main.activity_splash.*

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

//        ObjectAnimator.ofFloat(button,"translationX",500f).apply {
//            duration = 1000L
//            repeatMode = ValueAnimator.REVERSE
//            repeatCount = ValueAnimator.INFINITE
//            start()
//        }

        button setOnDebounceClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        investment.onInvestmentValueChangedListener = {
            button.text = it.toString()
        }
    }

}