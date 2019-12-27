package com.fundito.fundito.presentation.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnRepeat
import com.fundito.fundito.R
import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.removeLatestMoneyCharacter
import com.fundito.fundito.common.widget.showKeyboard
import com.fundito.fundito.presentation.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=3000 // 3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}


//        showKeyboard { number ->
//            val nextText = if (number != -1) {
//                val newNumberChar = (number + '0'.toInt()).toChar()
//
//                textView.text.toString().addCharForMoneyRepresentation(newNumberChar)
//            } else {
//                textView.text.toString().removeLatestMoneyCharacter()
//            }
//
//            ObjectAnimator.ofFloat(textView, "translationY", 0f, 30f).apply {
//                duration = 100L
//                repeatCount = 1
//                repeatMode = ObjectAnimator.REVERSE
//
//                doOnRepeat { textView.text = nextText }
//                start()
//            }
//
//            ObjectAnimator.ofFloat(textView, "alpha", 1f, 0.5f).apply {
//                duration = 100L
//                repeatCount = 1
//                repeatMode = ObjectAnimator.REVERSE
//
//                start()
//            }
//        }



