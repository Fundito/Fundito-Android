package com.fundito.fundito.common

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney

/**
 * Created by mj on 24, December, 2019
 */
fun View.fadeIn(duration : Long = 500L, delay : Long = 0L) {
    ObjectAnimator.ofFloat(this,"alpha",1f).apply {
        this.duration = duration
        startDelay = delay
        doOnStart {
            this@fadeIn.isVisible = true
        }
        start()
    }
}

fun View.fadeOut(duration: Long = 500L,delay : Long = 0L) {
    ObjectAnimator.ofFloat(this,"alpha",0f).apply {
        this.duration = duration
        startDelay = delay
        doOnEnd {
            this@fadeOut.isVisible = false
        }
        start()
    }
}

fun TextView.startMoneyAnimation(money : Int,suffix : String) {
    ValueAnimator.ofInt(0,money).apply {
        duration = 500L
        addUpdateListener {
            val value = it.animatedValue as Int
            this@startMoneyAnimation.text = "${value.toLong().toMoney()}$suffix"
        }
        start()
    }
    ObjectAnimator.ofFloat(this,"alpha",0f,1f).apply {
        duration = 500L
        start()
    }
}

infix fun <T> MutableLiveData<T>.post(value : T) {
    this.postValue(value)
}
fun Fragment.showAlert(message : String) {
    (requireActivity() as? AppCompatActivity)?.showAlert(message)
}
fun AppCompatActivity.showAlert(message : String) {
    AlertDialog.Builder(this)
        .setTitle(message)
        .setIcon(R.drawable.fundito_app_logo)
        .setPositiveButton("예") { _, _ ->}
        .show()
}