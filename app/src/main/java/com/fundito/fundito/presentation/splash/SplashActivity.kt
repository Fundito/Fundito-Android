package com.fundito.fundito.presentation.splash

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnRepeat
import com.fundito.fundito.R
import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.removeLatestMoneyCharacter
import com.fundito.fundito.common.widget.showKeyboard
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        showKeyboard { number ->
            val nextText = if (number != -1) {
                val newNumberChar = (number + '0'.toInt()).toChar()

                textView.text.toString().addCharForMoneyRepresentation(newNumberChar)
            } else {
                textView.text.toString().removeLatestMoneyCharacter()
            }

            ObjectAnimator.ofFloat(textView,"translationY",0f,30f).apply {
                duration = 100L
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE

                doOnRepeat {
                    textView.text = nextText
                }
                start()
            }

            ObjectAnimator.ofFloat(textView,"alpha",1f,0.5f).apply {
                duration = 100L
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE

                start()
            }
        }
    }
}
